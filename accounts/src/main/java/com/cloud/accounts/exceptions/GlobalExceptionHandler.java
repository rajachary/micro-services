package com.cloud.accounts.exceptions;


import com.cloud.accounts.dto.*;


import com.cloud.accounts.exceptions.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Global exception handler for the Account Ledger microservice.
 * <p>
 * This class handles various exceptions across the application and returns
 * standardized error responses. It extends ResponseEntityExceptionHandler to
 * leverage Spring's built-in exception handling mechanisms.
 * </p>
 *
 * @see ResponseEntityExceptionHandler
 */


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(errors -> {
            String fieldName = ((FieldError) errors).getField();
            String validationMsg = errors.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new  ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }


@ExceptionHandler(Exception.class)
public  ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
}
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException( ResourceNotFoundException resourceNotFoundException,
                                                                        WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                resourceNotFoundException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
}
}