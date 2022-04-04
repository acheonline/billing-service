package ru.achernayvskiy0n.billingservice.kafka;

import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;

/**
 */
public interface TransportConsumer {
    void createAccount(AccountCreationMessage message);
}
