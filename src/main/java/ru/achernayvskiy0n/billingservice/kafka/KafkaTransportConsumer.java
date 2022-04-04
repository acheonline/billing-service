package ru.achernayvskiy0n.billingservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryUserRepository;

/**
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransportConsumer implements TransportConsumer {

    @Autowired
    private InMemoryUserRepository repository;

    @KafkaListener(
            topics = {"${billing-service.transport.topics.account.create}"},
            containerFactory = "newsConsumerFactory")
    public void createAccount(AccountCreationMessage accountCreationMessage) {
        log.info("Получен запрос на создание аккаунта для нового пользователя: {}", accountCreationMessage.getUsername());
        repository.createAccountIdByClientId(accountCreationMessage.getUsername());
    }
}
