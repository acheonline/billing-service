package ru.achernayvskiy0n.billingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.achernayvskiy0n.billingservice.domain.ApiResponse;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryUserRepository;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;

/**
 *
 */
@RestController
@RequestMapping("api/v1/billing/account")
public class BillingServiceController {

    @Autowired
    private InMemoryUserRepository repository;

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
    ApiResponse create(@PathVariable String id) {
        var acc = repository.createAccountIdByClientId(id);
        return ApiResponse.builder()
                .message(acc)
                .status(HttpStatus.OK.value())
                .success(true)
                .build();
    }

    @PatchMapping("/decrease/{id}/{amount}")
    ApiResponse decreaseAccount(@PathVariable("id") String id, @PathVariable("amount") String amount) throws UserAccountInfoRepositoryException {
        repository.decreaseAccountByAccountId(id, amount);
        return ApiResponse.builder()
                .message("Сумма счета: '" + id + "' обновлена")
                .status(HttpStatus.OK.value())
                .success(true)
                .build();
    }

    @PatchMapping("/increase/{id}/{amount}")
    ApiResponse increaseAccount(@PathVariable("id") String id, @PathVariable("amount") String amount) {
        repository.increaseAccountByAccountId(id, amount);
        return ApiResponse.builder()
                .message("Сумма счета: '" + id + "' обновлена")
                .status(HttpStatus.OK.value())
                .success(true)
                .build();
    }
}
