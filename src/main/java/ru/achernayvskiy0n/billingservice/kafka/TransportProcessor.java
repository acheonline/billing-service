package ru.achernayvskiy0n.billingservice.kafka;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.RequestMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;

/**
 */
public interface TransportProcessor {
    void createAccount(AccountCreationMessage message) throws UserAccountInfoRepositoryException;
    ResponseMessage modifyAccount(@Payload RequestMessage message,
                                  @Header("X-Request-Id") String requestId) throws UserAccountInfoRepositoryException;
}
