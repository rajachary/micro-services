package com.cloud.accountsledger.service.client;


import com.cloud.accountsledger.entity.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "accounts", fallback = AccountFallBack.class)
public interface AccountsFeignClient {
    @PostMapping(value = "/accounts/{accountId}/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Accounts> postAccount(@PathVariable("accountId") String accountId,
                                                                            @RequestBody Accounts accounts);
}
