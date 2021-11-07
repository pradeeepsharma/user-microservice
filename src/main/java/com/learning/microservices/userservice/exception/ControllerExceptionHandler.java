package com.learning.microservices.userservice.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.function.Consumer;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleClientException(Throwable exception, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), "requested user not found");
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }
}
