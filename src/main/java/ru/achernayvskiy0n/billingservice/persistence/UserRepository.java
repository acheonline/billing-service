package ru.achernayvskiy0n.billingservice.persistence;


import ru.achernayvskiy0n.billingservice.client.UserAccountInfo;
import ru.achernayvskiy0n.billingservice.kafka.messages.ResponseMessage;

public interface UserRepository {

    UserAccountInfo getAccountIdByClientId(String id) throws UserAccountInfoRepositoryException;

    String createAccountIdByClientId(String id);

    void modifyAccountByAccountId(String accountId, UserAccountInfo userAccountInfo);
}