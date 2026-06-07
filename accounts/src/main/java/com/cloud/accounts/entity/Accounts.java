package com.cloud.accounts.entity;

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
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Data
public class Accounts {


    @Id
    String accountId;

    @NonNull
    Double balance;

    @NonNull
    String currency;
}
