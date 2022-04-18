package ru.achernayvskiy0n.billingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.achernayvskiy0n.billingservice.domain.Order;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;
import ru.achernayvskiy0n.billingservice.persistence.InMemoryUserRepository;
import ru.achernayvskiy0n.billingservice.persistence.UserAccountInfoRepositoryException;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BillingService {

    private final InMemoryUserRepository repository;

    public String createAccountIdByClientId(String clientId){
        return repository.createAccountIdByClientId(clientId);
    }

    public ResponseMessage modifyAccountByAccountId(String userId, Order order) throws UserAccountInfoRepositoryException {
        var amount = order.getAmount();
        var userAccountInfo = repository.getAccountIdByClientId(userId);
        if (userAccountInfo == null) {
            var errorPayload = "Пользователь с Id: '" + userId + "' не найден.";
            log.error(errorPayload);
            return createResponseMessage(errorPayload, userId, ResponseMessage.ApiResponseStatus.INTERNAL_SERVER_ERROR);
        } else {
            var clientAmount = userAccountInfo.getAmount();
            if ((clientAmount - amount) < 0) {
                var errorPayload = "На счете '" + userId + "' не достаточно средств для операции";
                log.error(errorPayload);
                return createResponseMessage(errorPayload, userId, ResponseMessage.ApiResponseStatus.INTERNAL_SERVER_ERROR);
            } else {
                clientAmount -= amount;
                userAccountInfo.setAmount(clientAmount);
                repository.modifyAccountByAccountId(userId, userAccountInfo);
                var successPayload = "Счет пользователя: '" + userId + "' успешно модифицирован.";
                log.info(successPayload);
                return createResponseMessage(successPayload, userId, ResponseMessage.ApiResponseStatus.OK);
            }
        }
    }

    private ResponseMessage createResponseMessage(String message, String userId, ResponseMessage.ApiResponseStatus status) {
        return ResponseMessage.builder().message(message).username(userId).status(status).build();
    }
}