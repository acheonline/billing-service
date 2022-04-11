package ru.achernayvskiy0n.billingservice.kafka.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.achernayvskiy0n.billingservice.domain.Order;

/**
 */
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage {
    private Order order;
}
