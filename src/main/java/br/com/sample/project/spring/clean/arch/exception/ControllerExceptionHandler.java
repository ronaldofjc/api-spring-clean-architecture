package br.com.sample.project.spring.clean.arch.exception;

import br.com.sample.project.spring.clean.arch.exception.entity.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final Set<ErrorResponse> errorResponse = processErrorsParameters(exception);
        return handleExceptionInternal(exception, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(final RuntimeException exception,
                                                          final WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .message(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(final RuntimeException exception,
                                                                final WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .message(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<Object> handleGatewayException(final RuntimeException exception,
                                                         final WebRequest request) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE.toString())
                .message(exception.getMessage())
                .build();
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    private Set<ErrorResponse> processErrorsParameters(final MethodArgumentNotValidException exception) {
        final BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        final Set<ErrorResponse> errors = new HashSet<>();

        fieldErrors.forEach(fieldError ->
                errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), fieldError.getDefaultMessage())));
        return errors;
    }
}
