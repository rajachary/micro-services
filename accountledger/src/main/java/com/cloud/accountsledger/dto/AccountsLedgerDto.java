package com.cloud.accountsledger.dto;

import com.cloud.accountsledger.entity.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsLedgerDto {
    @NotEmpty(message = "Event Id can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private String eventId;

    @NotEmpty(message = "Account Id can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private String accountId;

    @NotEmpty(message = "Type can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private String type;

    @NotEmpty(message = "Amount can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private Double amount;

    @NotEmpty(message = "Currency can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private String currency;

    @NotEmpty(message = "Event Timestamp can not be a null or empty")
    private String eventTimestamp;

    private MetaData metadata;

}
