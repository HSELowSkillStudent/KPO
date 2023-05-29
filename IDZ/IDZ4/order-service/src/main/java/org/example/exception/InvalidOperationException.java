package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * InvalidOperationException
 */
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class InvalidOperationException extends Exception {
    private HttpStatus httpStatus;
    private String message;
}
