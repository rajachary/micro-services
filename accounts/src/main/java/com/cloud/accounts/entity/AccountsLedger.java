package com.cloud.accounts.entity;

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

@Entity
@Getter
@Setter @ToString @AllArgsConstructor @NoArgsConstructor
@Slf4j
public class AccountsLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id
            ;
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

    public void setId(final Long id) {
        this.id = id;
    }

    public void setEventId(final String eventId) {
        this.eventId = eventId;
    }

    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setEventTimestamp(final String eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public void setBatchId(final String batchId) {
        this.batchId = batchId;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public Long getId() {
        return this.id;
    }

    public String getEventId() {
        return this.eventId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getType() {
        return this.type;
    }

    public Double getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getEventTimestamp() {
        return this.eventTimestamp;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public String getSource() {
        return this.source;
    }
}
