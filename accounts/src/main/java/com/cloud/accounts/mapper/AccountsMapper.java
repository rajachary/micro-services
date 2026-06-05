package com.cloud.accounts.mapper;

import com.cloud.accounts.entity.*;

public class AccountsMapper {
    public static Accounts mapToAccounts(AccountsLedger accountsLedgerDto, Accounts accounts){
        return accounts;
    }
    public static AccountsLedger mapToAccountsLedger(Accounts accounts, AccountsLedger accountsLedger){
        return accountsLedger;
    }
}
