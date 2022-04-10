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
        var userAccountInfo = new UserAccountInfo(clientId, accountId.toString(), "0");
        log.info("Аккаунт '{}' для пользователя '{}' создан", accountId, clientId);
        userStorage.put(clientId, userAccountInfo);
        return accountId.toString();
    }

    @Override
    public void decreaseAccountByAccountId(String accountId, String amount) throws UserAccountInfoRepositoryException {
        var userAccountInfo = userStorage.get(accountId);
        if (userAccountInfo == null) {
            throw new UserAccountInfoRepositoryException("Пользователь с Id: '" + accountId + "' не найден.");
        } else {
            var clientAmount = Double.parseDouble(userAccountInfo.getAmount());
            var am = Double.valueOf(amount);
            if ((clientAmount - am) < 0) {
                throw new UserAccountInfoRepositoryException("На счете '" + accountId + "' не достаточно средств для операции");
            } else {
                clientAmount -= am;
                userAccountInfo.setAmount(String.valueOf(clientAmount));
                userStorage.put(accountId, userAccountInfo);
            }
        }
    }

    @Override
    public void increaseAccountByAccountId(String accountId, String amount) throws UserAccountInfoRepositoryException {
        var userAccountInfo = userStorage.get(accountId);
        if (userAccountInfo == null) {
            throw new UserAccountInfoRepositoryException("Пользователь с Id: '" + accountId + "' не найден.");
        } else {
            var clientAmount = Double.parseDouble(userAccountInfo.getAmount());
            var am = Double.valueOf(amount);
            clientAmount += am;
            userAccountInfo.setAmount(String.valueOf(clientAmount));
            userStorage.put(accountId, userAccountInfo);
        }
    }
}
