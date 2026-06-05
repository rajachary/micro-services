package com.cloud.accountsledger.mapper;

import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;

public class AccountsLedgerMapper {
    public static AccountsLedger mapToAccountsLedger(AccountsLedgerDto accountsLedgerDto, AccountsLedger accountsLedger){
       accountsLedger.setEventId(accountsLedgerDto.getEventId());
       accountsLedger.setAccountId(accountsLedgerDto.getAccountId());
       accountsLedger.setType(accountsLedgerDto.getType());
       accountsLedger.setAmount(accountsLedgerDto.getAmount());
       accountsLedger.setCurrency(accountsLedgerDto.getCurrency());
       accountsLedger.setEventTimestamp(accountsLedgerDto. getEventTimestamp());
       accountsLedger.setBatchId(accountsLedgerDto.getMetaData().getBatchId());
       accountsLedger.setSource (accountsLedgerDto.getMetaData().getSource());
        return accountsLedger;
    }
    public static AccountsLedgerDto mapToAccountsLedgerDto(AccountsLedgerDto accountsLedgerDto, AccountsLedger accountsLedger){

        return accountsLedgerDto;
    }
}
