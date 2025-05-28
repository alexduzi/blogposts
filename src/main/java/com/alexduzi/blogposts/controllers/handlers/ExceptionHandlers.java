package com.alexduzi.blogposts.controllers.handlers;

import com.alexduzi.blogposts.exceptions.ResourceNotFoundException;
import com.alexduzi.blogposts.exceptions.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ResourceNotFoundException error, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error.getMessage(), error.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
