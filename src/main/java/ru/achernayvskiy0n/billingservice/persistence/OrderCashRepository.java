package ru.achernayvskiy0n.billingservice.persistence;

/**
 */
public interface OrderCashRepository {

    boolean addOrderId(String idempotencyOrderId) throws OrderCashRepositoryException;
}