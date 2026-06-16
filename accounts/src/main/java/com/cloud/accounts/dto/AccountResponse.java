package com.cloud.accounts.dto;

import lombok.*;

import java.math.*;
import java.util.*;

@Data
@Builder
public class AccountResponse {

    private Long accountId;
    private String accountNumber;
    private String accountHolderName;
    private String accountType;
    private BigDecimal balance;

    private List<TransactionSummary> recentTransactions;
}