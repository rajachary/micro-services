package com.cloud.accountsledger.repository;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface AccountsLedgerRepository extends JpaRepository<AccountsLedger, Long> {
    AccountsLedger findByEventId(String eventId);

    AccountsLedger findByAccountId(String accountId);

    List<AccountsLedger> findAllByAccountId(String accountId);
}
