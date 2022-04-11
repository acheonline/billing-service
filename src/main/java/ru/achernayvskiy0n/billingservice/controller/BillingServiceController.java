package ru.achernayvskiy0n.billingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.achernayvskiy0n.billingservice.domain.ApiResponse;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryUserRepository;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;
import ru.achernayvskiy0n.billingservice.service.BillingService;

/**
 *
 */
@RestController
@RequestMapping("api/v1/billing/account")
@RequiredArgsConstructor
public class BillingServiceController {

    private final InMemoryUserRepository repository;
    private final BillingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse returnAccountNo(@PathVariable("id") String id) throws UserAccountInfoRepositoryException {
        var acc = repository.getAccountIdByClientId(id);
        return ApiResponse.builder()
                .message(acc.getAccountNo())
                .status(HttpStatus.OK.value())
                .success(true)
                .build();
    }

    @PostMapping("/create/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse create(@PathVariable String id){
        var acc = service.createAccountIdByClientId(id);
        return ApiResponse.builder()
                .message(acc)
                .status(HttpStatus.CREATED.value())
                .success(true)
                .build();
    }
}
