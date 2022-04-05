package ru.achernayvskiy0n.billingservice.domain;

import lombok.*;
import lombok.experimental.Accessors;

@Builder
@Data
@Accessors
public class ApiResponse {

  private boolean success;
  private int status;
  private String message;
}
