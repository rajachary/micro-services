package com.cloud.accounts.controller;

import com.cloud.accounts.dto.*;
import com.cloud.accounts.entity.*;
import com.cloud.accounts.mapper.*;
import com.cloud.accounts.repository.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;



/*
 * Controller class for accounts
 * @author Kumar Thirunavukarasu
 * @version 1.0
 * @see AccountsApplication
 */

@RestController
@RequestMapping(path="/{accountId}", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class AccountsController {
    private AccountRepository  accountRepository;

    @GetMapping (path = "/transactions")
    public ResponseEntity<Accounts> postAccount(@Validated @PathVariable String accountId, @RequestBody Accounts accounts) {
        accountRepository.save(accounts);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }
    @GetMapping(path = "/balance")
    public ResponseEntity<String> getCurrentBalance(@Validated @PathVariable String accountId) {
        accountRepository.findByAccountId(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<String> getAccountDetailsAndRecentTransactions(@Validated @PathVariable String accountId) {
        accountRepository.findByAccountId(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
