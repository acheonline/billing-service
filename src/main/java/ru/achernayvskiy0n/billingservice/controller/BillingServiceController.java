package ru.achernayvskiy0n.billingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.achernayvskiy0n.billingservice.domain.ApiResponse;
import ru.achernayvskiy0n.billingservice.domain.Order;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;
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

    @PatchMapping("/{id}/increase/")
    @ResponseStatus(HttpStatus.CREATED)
    ApiResponse increaseBillingAccount(@PathVariable("id") String id, @RequestBody Order order) throws UserAccountInfoRepositoryException {
        var responseMessage = service.modifyAccountByAccountId(id, order);
        return ApiResponse.builder()
                .message(responseMessage.getMessage())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .build();
    }

    @GetMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse returnMoneyStatus(@PathVariable("id") String id) throws UserAccountInfoRepositoryException {
        var acc = repository.getAccountIdByClientId(id);
        return ApiResponse.builder()
                .message(acc.getAmount().toString())
                .status(HttpStatus.OK.value())
                .success(true)
                .build();
    }
}
