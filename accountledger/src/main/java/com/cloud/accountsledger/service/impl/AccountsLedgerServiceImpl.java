package com.cloud.accountsledger.service.impl;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.repository.*;
import com.cloud.accountsledger.service.*;
import com.cloud.accountsledger.service.client.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.validation.annotation.*;
import com.cloud.accountsledger.service.client.AccountsFeignClient;

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
    public AccountsLedger fetchAccountLedgerEventById(String eventId) {
        return accountsLedgerRepository.findByEventId(eventId);
    }

    @Override
    public List<AccountsLedger> fetchAccountLedgerByAccountId(String accountId) {
        AccountsLedger accountsLedger = accountsLedgerRepository.findByAccountId(accountId);
        return (List<AccountsLedger>) accountsLedgerRepository.findByAccountId(accountId);
    }

    @Override
    public double getBalance(AccountsLedgerDto accountsLedgerDto) {
        List<AccountsLedger> allByAccountId = accountsLedgerRepository.findAllByAccountId(accountsLedgerDto.getAccountId());
        OptionalDouble debitBalance = allByAccountId.stream()
                .filter(al -> al.getType().equals("DEBIT"))
                .mapToDouble(al -> al.getAmount())
                .reduce(Double::sum);
        OptionalDouble creditBalance = allByAccountId.stream()
                .filter(al -> al.getType().equals("CREDIT"))
                .mapToDouble(al -> al.getAmount())
                .reduce(Double::sum);

        if (debitBalance.isPresent() && creditBalance.isPresent()) {
            return debitBalance.getAsDouble() - creditBalance.getAsDouble();
        } else if (debitBalance.isPresent()) {
            return debitBalance.getAsDouble();
        } else if (creditBalance.isPresent()) {
            return -creditBalance.getAsDouble();
        } else {
            return 0.0;
        }
    }
}
