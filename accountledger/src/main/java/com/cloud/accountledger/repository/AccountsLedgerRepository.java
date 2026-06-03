package com.cloud.accountledger.repository;

import com.cloud.accountledger.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface AccountsLedgerRepository extends JpaRepository<AccountsLedger, Long> {
}
