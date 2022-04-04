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
    public UserAccountInfo getAccountIdByClientId(String id) {
        var userAccountInfo = userStorage.get(id);
        try {
            if (userAccountInfo == null) {
                throw new UserAccountInfoRepositoryException("В базе нет такого clientId: " + id);
            }
            log.info("Аккаунт {} для пользователя {} найден", userAccountInfo.getAccountNo(), id);
            return userAccountInfo;
        } catch (UserAccountInfoRepositoryException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String createAccountIdByClientId(String clientId) {
        var accountId = UUID.randomUUID();
        var userAccountInfo = new UserAccountInfo(clientId, accountId.toString(), 0.00d);
        log.info("Аккаунт {} для пользователя {} создан.", accountId, clientId);
        userStorage.put(clientId, userAccountInfo);
        return accountId.toString();
    }
}
