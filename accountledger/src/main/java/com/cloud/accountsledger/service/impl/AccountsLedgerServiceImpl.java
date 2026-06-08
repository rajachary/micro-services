package com.cloud.accountsledger.service.impl;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.repository.*;
import com.cloud.accountsledger.service.*;
import com.cloud.accountsledger.service.client.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AccountsLedgerServiceImpl implements AccountLedgerService {

    private  AccountsLedgerRepository accountsLedgerRepository;
    private AccountsFeignClient accountsFeignClient;


    @Override
    public AccountsLedger saveAccountLedgerEvent(AccountsLedger accountsLedger) {
        return accountsLedgerRepository.save(accountsLedger);
    }

    @Override
    public AccountsLedgerDto fetchAccountLedgerEventById(String eventId) {
        return accountsLedgerRepository.findByEventId(eventId);
    }

    @Override
    public AccountsLedger fetchAccountLedgerByAccountId(String accountId) {
        AccountsLedger accountsLedger = accountsLedgerRepository.findByAccountId(accountId);
        return accountsLedgerRepository.findByAccountId(accountId);
    }
}
