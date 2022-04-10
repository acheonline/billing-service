package ru.achernayvskiy0n.billingservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryUserRepository;

/**
 *
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "spring", name = "profiles.active", havingValue = "prod")
@RequiredArgsConstructor
public class KafkaTransportConsumer implements TransportConsumer {

    @Autowired
    private InMemoryUserRepository repository;

    @KafkaListener(
            topics = {"${billing-service.transport.topics.account.create}"},
            containerFactory = "newsConsumerFactory")
    public void createAccount(@NonNull AccountCreationMessage accountCreationMessage) {
        log.info("Получен запрос на создание аккаунта для нового пользователя: {}", accountCreationMessage.getUsername());
        repository.createAccountIdByClientId(accountCreationMessage.getUsername());
    }
}
