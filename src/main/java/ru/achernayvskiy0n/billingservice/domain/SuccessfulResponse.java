package ru.achernayvskiy0n.billingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessfulResponse {

  private final boolean success;
  private int status;
  private String message;
}
