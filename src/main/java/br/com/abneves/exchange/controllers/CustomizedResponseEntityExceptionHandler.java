package br.com.abneves.exchange.controllers;

import br.com.abneves.exchange.configuration.exceptions.NotFoundException;
import br.com.abneves.exchange.controllers.vos.responses.FailResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_MESSAGE_DEFAULT = "Invalid request values.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        final var errorsMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        final var response = FailResponse.of(VALIDATION_MESSAGE_DEFAULT, errorsMessages);
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<FailResponse> handleNotFoundExceptions(
            NotFoundException ex, WebRequest request) {
        var response = FailResponse.of(request.getDescription(Boolean.FALSE), List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<FailResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var response = FailResponse.of(request.getDescription(Boolean.FALSE), List.of(ex.getMessage()));
        return ResponseEntity.unprocessableEntity().body(response);
    }
}
