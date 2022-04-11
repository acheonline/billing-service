package ru.achernayvskiy0n.billingservice.domain;

import lombok.*;

/**
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private String username;
    private Double amount;
}
