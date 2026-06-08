package com.cloud.accounts.service;

import com.cloud.accounts.dto.*;

public interface AccountsService {
    AccountsDto saveAccount(AccountsDto accountsDto);
    AccountsDto fetchAccountByAccountId(String accountId);
    Double getBalance(String accountId);
}
