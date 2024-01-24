package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessage globalIllegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ExceptionMessage(exception.getMessage());
    }
}
