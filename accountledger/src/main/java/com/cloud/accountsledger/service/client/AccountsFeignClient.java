package com.cloud.accountsledger.service.client;


import com.cloud.accountsledger.entity.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

@FeignClient(name = "accounts")
public interface AccountsFeignClient {
    @PostMapping (path = "/transactions")
    public ResponseEntity<Accounts> postAccount(@PathVariable String accountId, @RequestBody Accounts accounts) ;

}
