package io.upschool.exception;

import io.upschool.dto.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        final var errorMessage =
                MessageFormat.format("Cause of Exception; {0}  {1}", ex.getCause().getMessage(), ex.getClass());

        var response = BaseResponse.<Object>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errorMessage)
                .isSuccess(false)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(final BusinessException exception, final WebRequest request) {
        var response = BaseResponse.<Object>builder()
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .error(exception.getMessage())
                .isSuccess(false)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {

        final var errorMessage =
                MessageFormat.format("No handler found for {0} {1}", ex.getHttpMethod(), ex.getRequestURL());
        System.out.println(errorMessage);
        var response = BaseResponse.<Object>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(errorMessage)
                .isSuccess(false)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        var response = BaseResponse.<Object>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(errors.toString())
                .isSuccess(false)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
