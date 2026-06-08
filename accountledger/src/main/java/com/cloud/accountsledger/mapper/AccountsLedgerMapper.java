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
         accounts.setBalance(getBalance(accountsLedgerDto));
         accounts.setCurrency(accountsLedgerDto.getCurrency());
        return accounts;
    }
    private  double getBalance(AccountsLedgerDto accountsLedgerDto) {
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
