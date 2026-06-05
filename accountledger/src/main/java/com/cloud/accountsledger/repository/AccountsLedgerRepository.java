package com.cloud.accountsledger.repository;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface AccountsLedgerRepository extends JpaRepository<AccountsLedger, Long> {
    AccountsLedgerDto findByEventId(String eventId);

    AccountsLedgerDto findByAccountId(String accountId);
}
