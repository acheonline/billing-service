package ru.achernayvskiy0n.billingservice.persistence;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 */
@Repository
public class InMemoryOrderCashRepository implements OrderCashRepository {
    private final Set<String> storage;

    public InMemoryOrderCashRepository() {
        this.storage = new HashSet<>();
    }

    @Override
    public boolean addOrderId(String idempotencyOrderId) throws OrderCashRepositoryException {
        if (storage.contains(idempotencyOrderId)) {
            throw new OrderCashRepositoryException("Заказ с id: '" + idempotencyOrderId + "' уже был создан");
        } else {
            storage.add(idempotencyOrderId);
            return true;
        }
    }
}