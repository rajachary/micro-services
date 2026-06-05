package com.cloud.accountsledger.controller;


import com.cloud.accountsledger.dto.*;
import com.cloud.accountsledger.entity.*;
import com.cloud.accountsledger.mapper.*;
import com.cloud.accountsledger.service.*;
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
@Slf4j
public class AccountsLedgerController {

    private static final Logger log = LoggerFactory.getLogger(AccountsLedgerController.class);
    private AccountLedgerService accountLedgerService;

    @PostMapping("/")
    public ResponseEntity<AccountsLedger> postEvent(@Validated @RequestBody AccountsLedgerDto accountsLedgerDto) {
       log.info("Event received: {}", accountsLedgerDto);
        AccountsLedger accountsLedger = AccountsLedgerMapper.mapToAccountsLedger(accountsLedgerDto, new AccountsLedger());
        accountLedgerService.saveAccountLedgerEvent(accountsLedger);;
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<List<AccountsLedger>> getEventbyId(@Validated
            @RequestParam @Pattern(regexp="(^$|[0-9][a-z][A-Z])", message = "Id must be alpha numeric") String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "account={accountId}")
    public ResponseEntity<AccountsLedger> getEventByAccountId( @Validated
            @RequestParam @Pattern(regexp="(^$|[0-9][a-z][A-Z])", message = "Id must be alpha numeric") String accountId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
