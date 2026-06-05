package com.cloud.accountsledger.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;


/*
 * Entity class for account ledger
 * @author Kumar Thirunavukarasu
 * @version 1.0
 * @since 2023-01-01
 * @see AccountsApplication
 *     | `eventId` | string | Yes | Unique identifier for the event |
            | `accountId` | string | Yes | The account this event belongs to |
            | `type` | string | Yes | Must be `"CREDIT"` or `"DEBIT"` |
            | `amount` | number | Yes | Must be greater than 0 |
            | `currency` | string | Yes | e.g., `"USD"` |
            | `eventTimestamp` | string (ISO 8601) | Yes | When the event originally occurred |
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity
public class AccountsLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String eventId;

    @NonNull
    private String accountId;

    @NonNull
    private String type;

    @NonNull
    private Double amount;

    @NonNull
    private String currency;

    @NonNull
    private String eventTimestamp;

    private String batchId;
    private String source;

}
