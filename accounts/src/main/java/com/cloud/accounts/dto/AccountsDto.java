package com.cloud.accounts.dto;

import lombok.*;

/*
 * DTO class for accounts
 * @author Kumar Thirunavukarasu
 * @version 1.0
 * @since 2023-01-01
 * @see AccountsApplication
 *   "eventId": "evt-001",
      "accountId": "acct-123",
      "type": "CREDIT",
      "amount": 150.00,
      "currency": "USD",
 */
@Setter
@Getter
public class AccountsDto {
    private String eventId;
    private String accountId;
    private String type;
    private Double amount;
    private String currency;
}
