package com.cloud.accountsledger.entity;

import jakarta.persistence.*;
import lombok.*;


/*
 * Entity class for accounts
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


@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
public class Accounts {

    @Id
    @NonNull
    String accountId;

    @NonNull
    Double amount;

    @NonNull
    String type;

    @NonNull
    String currency;
}
