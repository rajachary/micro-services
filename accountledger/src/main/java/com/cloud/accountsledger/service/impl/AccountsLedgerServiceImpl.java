package com.cloud.accountsledger.service.impl;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.repository.*;
import com.cloud.accountsledger.service.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
public class AccountsLedgerServiceImpl implements AccountLedgerService {

    private  AccountsLedgerRepository accountsLedgerRepository;


    @Override
    public AccountsLedger saveAccountLedgerEvent(AccountsLedger accountsLedger) {
        return accountsLedgerRepository.save(accountsLedger);
    }

    @Override
    public AccountsLedgerDto fetchAccountLedgerEventById(String eventId) {
        return accountsLedgerRepository.findByEventId(eventId);
    }

    @Override
    public AccountsLedgerDto fetchAccountLedgerByAccountId(String AccountId) {
        return accountsLedgerRepository.findByAccountId(AccountId);
    }
}
