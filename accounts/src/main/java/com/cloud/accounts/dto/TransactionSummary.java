package com.cloud.accounts.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionSummary {

    private Long transactionId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
}