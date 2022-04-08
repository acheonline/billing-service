package ru.achernayvskiy0n.billingservice.kafka.messages;

import lombok.*;
import lombok.experimental.Accessors;

/**
 */
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationMessage {
    private String username;
}
