package com.makeen.assignment.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger();

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        logger.error( "NotFoundException {}\n", request.getRequestURI(), ex );

        return ResponseEntity
                .status( HttpStatus.NOT_FOUND )
                .body( "Not found exception" + ex.getMessage() );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(HttpServletRequest request, ValidationException ex) {
        logger.error( "ValidationException {}\n", request.getRequestURI(), ex );

        return ResponseEntity
                .badRequest()
                .body( "Validation exception" + ex.getMessage() );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        logger.error( "handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex );

        return ResponseEntity
                .badRequest()
                .body( "Missing request parameter" + ex.getMessage() );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        logger.error( "handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex );

        Map<String, String> details = new HashMap<>();
        details.put( "paramName", ex.getName() );
        details.put( "paramValue", ofNullable( ex.getValue() ).map( Object::toString ).orElse( "" ) );
        details.put( "errorMessage", ex.getMessage() );

        return ResponseEntity
                .badRequest()
                .body( details );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logger.error( "handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex );

        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach( fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put( "objectName", fieldError.getObjectName() );
                    detail.put( "field", fieldError.getField() );
                    detail.put( "rejectedValue", "" + fieldError.getRejectedValue() );
                    detail.put( "errorMessage", fieldError.getDefaultMessage() );
                    details.add( detail );
                } );

        return ResponseEntity
                .badRequest()
                .body( details );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        logger.error( "handleAccessDeniedException {}\n", request.getRequestURI(), ex );

        return ResponseEntity
                .status( HttpStatus.FORBIDDEN )
                .body( "Access denied!" + ex.getMessage() );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(HttpServletRequest request, Exception ex) {
        logger.error( "handleInternalServerError {}\n", request.getRequestURI(), ex );

        return ResponseEntity
                .status( HttpStatus.INTERNAL_SERVER_ERROR )
                .body( "Internal server error" + ex.getMessage() );
    }
}



