package ru.achernayvskiy0n.billingservice.kafka.messages;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 */
@Data
@Accessors
@RequiredArgsConstructor
@Builder
public class AccountCreationMessage {
    private final String username;
}
