package ru.achernayvskiy0n.billingservice.persistence;


import ru.achernayvskiy0n.billingservice.client.UserAccountInfo;

public interface UserRepository {

    UserAccountInfo getAccountIdByClientId(String id) throws UserAccountInfoRepositoryException;

    String createAccountIdByClientId(String id);

    void decreaseAccountByAccountId(String accountId, String amount) throws UserAccountInfoRepositoryException;

    void increaseAccountByAccountId(String accountId, String amount);
}