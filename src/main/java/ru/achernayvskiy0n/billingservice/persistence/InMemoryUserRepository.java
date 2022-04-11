package ru.achernayvskiy0n.billingservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.achernayvskiy0n.billingservice.client.UserAccountInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, UserAccountInfo> userStorage;

    public InMemoryUserRepository() {
        this.userStorage = new HashMap<>();
    }

    @Override
    public UserAccountInfo getAccountIdByClientId(String id) throws UserAccountInfoRepositoryException {
        var userAccountInfo = userStorage.get(id);
        if (userAccountInfo == null) {
            throw new UserAccountInfoRepositoryException("В базе нет такого clientId: '" + id + "'");
        }
        log.info("Аккаунт '{}' для пользователя '{}' найден", userAccountInfo.getAccountNo(), id);
        return userAccountInfo;
    }

    @Override
    public String createAccountIdByClientId(String clientId) {
        var accountId = UUID.randomUUID();
        var userAccountInfo = new UserAccountInfo(clientId, accountId.toString(), 0.0);
        log.info("Аккаунт '{}' для пользователя '{}' создан", accountId, clientId);
        userStorage.putIfAbsent(clientId, userAccountInfo);
        return userAccountInfo.getAccountNo();
    }

    @Override
    public void modifyAccountByAccountId(String userId, UserAccountInfo userAccountInfo) {
        userStorage.put(userId, userAccountInfo);
    }
}
