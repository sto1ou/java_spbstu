package com.example.tasks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Для 4хх
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientException extends RuntimeException {

    public ClientException(final String message) {
        super(message);
    }

    public ClientException() {
        super();
    }
}
