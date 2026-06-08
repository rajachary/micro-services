package com.cloud.accountsledger.service;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;

import java.util.*;

public interface AccountLedgerService {

    AccountsLedger saveAccountLedgerEvent(AccountsLedger accountsLedger);

    AccountsLedger fetchAccountLedgerEventById(String eventId);

    List<AccountsLedger> fetchAccountLedgerByAccountId(String AccountId);
}
