package com.cloud.accounts.controller;

import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

/*
 * Controller class for accounts
 * @author Kumar Thirunavukarasu
 * @version 1.0
 * @since 2023-01-01
 * @see AccountsApplication
 */

@RestController
@RequestMapping(path="/accounts/{accountId}", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {
    @GetMapping (path = "/transactions")
    public ResponseEntity<String> postAccount() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(path = "/balance")
    public ResponseEntity<String> getCurrentBalance() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<String> getAccountDetailsAndRecentTransactions() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
