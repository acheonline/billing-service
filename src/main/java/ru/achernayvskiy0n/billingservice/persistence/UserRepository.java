package ru.achernayvskiy0n.billingservice.persistence;


import ru.achernayvskiy0n.billingservice.client.UserAccountInfo;

public interface UserRepository {

    UserAccountInfo getAccountIdByClientId(String id) throws UserAccountInfoRepositoryException;

    String createAccountIdByClientId(String id);
}