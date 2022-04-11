package ru.achernayvskiy0n.billingservice.kafka;

import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.RequestMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;

/**
 */
public interface TransportProcessor {
    void createAccount(AccountCreationMessage message) throws UserAccountInfoRepositoryException;
    ResponseMessage modifyAccount(RequestMessage message) throws UserAccountInfoRepositoryException;
}
