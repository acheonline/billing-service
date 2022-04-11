package ru.achernayvskiy0n.billingservice.client;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInfo {

  private String clientId;
  private String accountNo;
  private Double amount;
}
