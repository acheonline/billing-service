package ru.achernayvskiy0n.billingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.achernayvskiy0n.billingservice.client.UserAccountInfo;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryUserRepository;

/**
 *
 */
@RestController
@RequestMapping("api/v1/billing/account")
public class BillingServiceController {

    @Autowired
    private InMemoryUserRepository repository;

    @GetMapping("/{id}")
    UserAccountInfo returnAccountNo(@PathVariable("id") String id) {
        return repository.getAccountIdByClientId(id);
    }

    @PostMapping("/create/{id}")
    ResponseEntity<String> create(@PathVariable String id) {
        return ResponseEntity.ok(repository.createAccountIdByClientId(id));
    }
}
