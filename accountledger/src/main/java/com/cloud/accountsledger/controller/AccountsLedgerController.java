package com.cloud.accountsledger.controller;


import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.mapper.*;
import com.cloud.accountsledger.service.*;
import com.cloud.accountsledger.service.client.*;
import jakarta.transaction.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.java.*;
import lombok.extern.slf4j.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;


/*
 * Controller class for accounts
 * @author Kumar Thirunavukarasu
 * @version 1.0
 * @since 2023-01-01
 * @see AccountsApplication
 */

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsLedgerController {

    private static final Logger log = LoggerFactory.getLogger(AccountsLedgerController.class);
    private AccountLedgerService accountLedgerService;
    private AccountsFeignClient accountsLedgerFeignClient;
    private AccountsLedgerMapper accountsLedgerMapper;

    @PostMapping()
    @Transactional
    public ResponseEntity<AccountsLedger> postEvent(@Validated @RequestBody AccountsLedgerDto accountsLedgerDto) {
       log.info("Event received: {}", accountsLedgerDto);
        AccountsLedger accountsLedger = AccountsLedgerMapper.mapToAccountsLedger(accountsLedgerDto, new AccountsLedger());
        accountLedgerService.saveAccountLedgerEvent(accountsLedger);
        accountLedgerService.getBalance(accountsLedgerDto);
        Accounts accounts = accountsLedgerMapper.mapToAccounts(accountsLedgerDto, new Accounts());
        accountsLedgerFeignClient.postAccount(accounts.getAccountId(), accounts);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountsLedger> getEventbyId(@Validated
            @RequestParam @Pattern(regexp="(^$|[0-9][a-z][A-Z])", message = "Id must be alpha numeric") String id) {
        AccountsLedger accountsLedger = accountLedgerService.fetchAccountLedgerEventById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(accountsLedger);
    }

    @GetMapping(path = "account={accountId}")
    public ResponseEntity<List<AccountsLedger>> getEventByAccountId( @Validated
            @RequestParam @Pattern(regexp="(^$|[0-9][a-z][A-Z])", message = "Id must be alpha numeric") String accountId) {
        List<AccountsLedger> accountsLedger = accountLedgerService.fetchAccountLedgerByAccountId(accountId);
        Stream<AccountsLedger> sortedAccountLedger = accountsLedger.stream().sorted(Comparator.comparing(AccountsLedger::getEventTimestamp));
        return ResponseEntity.accepted().body(accountsLedger);
    }
}
