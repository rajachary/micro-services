package com.cloud.accountledger.controller;

import com.cloud.accountledger.entity.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
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
@RequestMapping(path="/events", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsLedgerController {
    @PostMapping
    public ResponseEntity<AccountsLedger> postEvent(@Valid @RequestBody AccountsLedger accountLedger) {
        return new ResponseEntity<>(accountLedger, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<List<AccountsLedger>> getEventbyId(
            @RequestParam @Pattern(regexp="(^$|[0-9][a-z][A-Z])", message = "Id must be alpha numeric") String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "account={accountId}")
    public ResponseEntity<AccountsLedger> getEventByAccountId() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
