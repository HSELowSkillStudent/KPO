package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.Constants;
import org.example.dto.jwt.JwtHeaderDto;
import org.example.dto.jwt.JwtPayloadDto;
import org.example.exception.InvalidOperationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * TokenChecker
 */
public class TokenChecker {
    /**
     * Get signature
     * @param accessToken accessToken
     * @return signature
     * @throws InvalidOperationException InvalidOperationException
     */
    public static JwtPayloadDto getPayloadFromToken(String accessToken) throws InvalidOperationException {
        if (!TokenChecker.tokenIsValid(accessToken)) {
            throw new InvalidOperationException(HttpStatus.UNAUTHORIZED, Constants.HttpMessages.UNAUTHORIZED);
        }
        JwtPayloadDto payloadDto = TokenChecker.getPayload(accessToken);
        assert payloadDto != null;
        String payloadCheck = TokenChecker.checkPayload(payloadDto);
        if (!"ok".equals(payloadCheck)) {
            throw new InvalidOperationException(HttpStatus.UNAUTHORIZED, payloadCheck);
        }
        return payloadDto;
    }

    /**
     * Check payload
     * @param accessToken accessToken
     * @return True if payload is valid else false
     */
    private static boolean tokenIsValid(String accessToken) {
        String[] parts = accessToken.split("\\.");
        if (parts.length != 3) {
            return false;
        }
        String header;
        String payload;
        String hashed;
        try {
            header = new String(Base64.decodeBase64(parts[0]));
            payload = new String(Base64.decodeBase64(parts[1]));
            hashed = getSignature(parts[0] + "." + parts[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (!hashed.equals(parts[2])) {
            return false;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(header, JwtHeaderDto.class);
            objectMapper.readValue(payload, JwtPayloadDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Get payload
     * @param accessToken accessToken
     * @return payload
     */
    private static JwtPayloadDto getPayload(String accessToken) {
        String[] parts = accessToken.split("\\.");
        String payload = new String(Base64.decodeBase64(parts[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, JwtPayloadDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check payload
     * @param payloadDto payloadDto
     * @return message
     */
    private static String checkPayload(JwtPayloadDto payloadDto) {
        Long notBefore = payloadDto.getNbf();
        Long expiredIn = payloadDto.getExp();
        Long current = System.currentTimeMillis();
        if (expiredIn < current) {
            return "token is expired";
        } else if (current < notBefore) {
            return "token creation fail";
        }
        return "ok";
    }

    /**
     * Get signature
     * @param data data
     * @return signature
     */
    private static String getSignature(String data) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Constants.TokenChecker.SECRET_KEY.getBytes(), Constants.TokenChecker.ALGORITHM);
        Mac mac;
        try {
            mac = Mac.getInstance(Constants.TokenChecker.ALGORITHM);
            mac.init(secretKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return java.util.Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
    }
}
