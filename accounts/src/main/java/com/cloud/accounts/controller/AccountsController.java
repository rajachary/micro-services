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
@RequestMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class AccountsController {
    private AccountRepository  accountRepository;

    @PostMapping(value = "/{accountId}/transactions")
    public ResponseEntity<Accounts> postAccount(@Validated @PathVariable("accountId") String accountId, @RequestBody Accounts accounts) {
        accountRepository.save(accounts);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }
    @GetMapping(value = "/{accountId}/balance")
    public ResponseEntity<String> getCurrentBalance(@Validated @PathVariable String accountId) {
        accountRepository.findByAccountId(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<String> getAccountDetailsAndRecentTransactions(@Validated @PathVariable String accountId) {
        accountRepository.findByAccountId(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
