package ru.achernayvskiy0n.billingservice.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.achernayvskiy0n.billingservice.domain.ApiResponse;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;

@Slf4j
@NoArgsConstructor
@RestControllerAdvice(assignableTypes = BillingServiceController.class)
public class RestExceptionHandler {

    @ExceptionHandler(UserAccountInfoRepositoryException.class)
    protected ResponseEntity<ApiResponse> handleConflict(UserAccountInfoRepositoryException ex) {
        var rootCause = ex.getCause();
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var err = ApiResponse.builder()
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        log.error(err.toString(), rootCause);
        return ResponseEntity.status(httpStatus.value()).body(err);
    }
}