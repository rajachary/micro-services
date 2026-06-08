package com.cloud.accountsledger.mapper;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.repository.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class AccountsLedgerMapper {

    private static final Logger log = LoggerFactory.getLogger(AccountsLedgerMapper.class);
    private AccountsLedgerRepository accountsLedgerRepository;

    public static AccountsLedger mapToAccountsLedger(AccountsLedgerDto accountsLedgerDto, AccountsLedger accountsLedger){
       accountsLedger.setEventId(accountsLedgerDto.getEventId());
       accountsLedger.setAccountId(accountsLedgerDto.getAccountId());
       accountsLedger.setType(accountsLedgerDto.getType());
       accountsLedger.setAmount(accountsLedgerDto.getAmount());
       accountsLedger.setCurrency(accountsLedgerDto.getCurrency());
       accountsLedger.setEventTimestamp(accountsLedgerDto. getEventTimestamp());
       accountsLedger.setBatchId(accountsLedgerDto.getMetadata().getBatchId());
       accountsLedger.setSource (accountsLedgerDto.getMetadata().getSource());
        return accountsLedger;
    }
    public Accounts mapToAccounts(AccountsLedgerDto accountsLedgerDto, Accounts accounts){
         accounts.setAccountId(accountsLedgerDto.getAccountId());
         //accounts.setBalance(getBalance(accountsLedgerDto));
         accounts.setCurrency(accountsLedgerDto.getCurrency());
        return accounts;
    }

}
