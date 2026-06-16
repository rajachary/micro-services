package com.cloud.accountsledger.service.impl;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.mapper.*;
import com.cloud.accountsledger.repository.*;
import com.cloud.accountsledger.service.*;
import lombok.*;
import org.springframework.stereotype.*;
import com.cloud.accountsledger.service.client.AccountsFeignClient;

import java.util.*;

@Service
@AllArgsConstructor
public class AccountsLedgerServiceImpl implements AccountLedgerService {

    private  AccountsLedgerRepository accountsLedgerRepository;
    private AccountsFeignClient accountsFeignClient;
    private AccountsLedgerMapper accountsLedgerMapper;


    @Override
    public AccountsLedger saveAccountLedgerEvent(AccountsLedgerDto accountsLedgerDto) {
        AccountsLedger accountsLedger = accountsLedgerMapper.mapToAccountLedger(accountsLedgerDto, new  AccountsLedger());
        Optional<Double> balance = getBalance(accountsLedger.getAccountId());
        Accounts accounts = accountsLedgerMapper.mapToAccounts(accountsLedgerDto, new Accounts());
        accounts.setAmount(balance.orElse(0.0));
        accountsFeignClient.postAccount(accounts.getAccountId(), accounts);
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
    public Optional<Double> getBalance(String accountId) {
        List<AccountsLedger> allByAccountId = accountsLedgerRepository.findAllByAccountId(accountId);
        OptionalDouble debitBalance = allByAccountId.stream()
                .filter(al -> al.getType().equals("DEBIT"))
                .mapToDouble(al -> al.getAmount())
                .reduce(Double::sum);
        OptionalDouble creditBalance = allByAccountId.stream()
                .filter(al -> al.getType().equals("CREDIT"))
                .mapToDouble(al -> al.getAmount())
                .reduce(Double::sum);

        if (debitBalance.isPresent() && creditBalance.isPresent()) {
            return Optional.of(debitBalance.getAsDouble() - creditBalance.getAsDouble());
        } else if (debitBalance.isPresent()) {
            return Optional.of(debitBalance.getAsDouble());
        } else if (creditBalance.isPresent()) {
            return Optional.of(-creditBalance.getAsDouble());
        } else {
            return Optional.of(0.0);
        }
    }
}
