package com.cloud.accountsledger.service;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;

public interface AccountLedgerService {

    AccountsLedger saveAccountLedgerEvent(AccountsLedger accountsLedger);

    AccountsLedgerDto fetchAccountLedgerEventById(String eventId);

    AccountsLedgerDto fetchAccountLedgerByAccountId(String AccountId);
}
