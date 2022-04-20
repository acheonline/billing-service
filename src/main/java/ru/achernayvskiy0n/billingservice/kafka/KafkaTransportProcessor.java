package ru.achernayvskiy0n.billingservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.achernayvskiy0n.billingservice.kafka.messages.AccountCreationMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.RequestMessage;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryOrderCashRepository;
import ru.achernayvskiy0n.billingservice.persistence.OrderCashRepositoryException;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;
import ru.achernayvskiy0n.billingservice.service.BillingService;

/**
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransportProcessor implements TransportProcessor {

    private final BillingService service;

    private final InMemoryOrderCashRepository cashRepository;

    @KafkaListener(
            topics = {"${billing-service.transport.topics.account.create}"},
            containerFactory = "accountCreateConsumerFactory")
    public void createAccount(@NonNull AccountCreationMessage accountCreationMessage) {
        log.info("Получен запрос на создание аккаунта для нового пользователя: {}", accountCreationMessage.getUsername());
        service.createAccountIdByClientId(accountCreationMessage.getUsername());
    }

    @KafkaListener(
            topics = {"${billing-service.transport.topics.order.request}"},
            containerFactory = "requestListenerContainerFactory")
    @SendTo
    public ResponseMessage modifyAccount(@Payload RequestMessage message,
                                         @Header("X-Request-Id") String requestId) throws UserAccountInfoRepositoryException  {
        log.info("Получен запрос на модификацию счета пользователя: {}", message.getOrder().getUsername());
        try {
            cashRepository.addOrderId(requestId);
            return service.modifyAccountByAccountId(message.getOrder().getUsername(), message.getOrder());
        } catch (OrderCashRepositoryException ex) {
            var errMessage = "Заказ с id: '" + requestId + "' уже создан.";
            log.error(errMessage, ex);
            return ResponseMessage.builder()
                    .status(ResponseMessage.ApiResponseStatus.CONFLICT)
                    .username(message.getOrder().getUsername())
                    .message(errMessage)
                    .build();
        }
    }
}
