package org.example.service;

import constants.Constants;
import org.example.domain.Role;
import org.example.domain.entity.Dish;
import org.example.domain.repository.DishRepository;
import org.example.dto.jwt.JwtPayloadDto;
import org.example.dto.request.CreateDishDto;
import org.example.dto.request.DeleteDishDto;
import org.example.dto.response.ClientDishDto;
import org.example.dto.response.ManagerDishDto;
import org.example.dto.response.DishResponseDto;
import org.example.exception.InvalidOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DishService
 */
@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    /**
     * Get menu
     * @param token jwt token
     * @return List<? extends DishResponseDto>
     * @throws InvalidOperationException if token is invalid
     */
    public List<? extends DishResponseDto> getMenu(String token) throws InvalidOperationException {
        JwtPayloadDto payloadDto = TokenChecker.getPayloadFromToken(token);
        List<Dish> dishes = dishRepository.findAll();
        if (Objects.equals(payloadDto.getRole(), Role.MANAGER)) {
            return dishes.stream().map(x -> new ManagerDishDto()
                    .setId(x.getId())
                    .setQuantity(x.getQuantity())
                    .setName(x.getName())
                    .setDescription(x.getDescription())
                    .setPrice(x.getPrice())
            ).collect(Collectors.toList());
        }
        return dishes.stream().map(x -> new ClientDishDto()
                .setAvailable(x.getQuantity() > 0)
                .setPrice(x.getPrice())
                .setName(x.getName())
                .setDescription(x.getDescription())
        ).collect(Collectors.toList());
    }

    /**
     * Add dish
     * @param token jwt token
     * @param dishRequestDto CreateDishDto
     * @return String
     * @throws InvalidOperationException if token is invalid
     */
    public String addDish(String token, CreateDishDto dishRequestDto) throws InvalidOperationException {
        JwtPayloadDto payloadDto = TokenChecker.getPayloadFromToken(token);
        if (!Objects.equals(Role.MANAGER, payloadDto.getRole())) {
            throw new InvalidOperationException(HttpStatus.FORBIDDEN, Constants.HttpMessages.FORBIDDEN);
        }
        if (dishRepository.existsByName(dishRequestDto.getName())) {
            Dish dish = dishRepository.findByName(dishRequestDto.getName());
            dish.setDescription(dishRequestDto.getDescription())
                    .setPrice(dishRequestDto.getPrice())
                    .setQuantity(dishRequestDto.getQuantity());
            dishRepository.save(dish);
            return Constants.Messages.UPDATED;
        }
        dishRepository.save(new Dish()
                    .setDescription(dishRequestDto.getDescription())
                    .setPrice(dishRequestDto.getPrice())
                    .setQuantity(dishRequestDto.getQuantity())
                    .setName(dishRequestDto.getName())
        );
        return Constants.Messages.SAVED;
    }

    /**
     * Delete dish
     * @param token jwt token
     * @param requestDto DeleteDishDto
     * @return String - Success
     * @throws InvalidOperationException if token is invalid
     */
    public String deleteDish(String token, DeleteDishDto requestDto) throws InvalidOperationException {
        JwtPayloadDto payloadDto = TokenChecker.getPayloadFromToken(token);
        if (!Objects.equals(Role.MANAGER, payloadDto.getRole())) {
            throw new InvalidOperationException(HttpStatus.FORBIDDEN, Constants.HttpMessages.FORBIDDEN);
        }
        if (!dishRepository.existsByName(requestDto.getDishName())) {
            throw new InvalidOperationException(HttpStatus.NOT_FOUND, Constants.HttpMessages.NOT_FOUND);
        }
        dishRepository.deleteByName(requestDto.getDishName());
        return Constants.DishService.SUCCESS;
    }
}
