package com.cloud.accountsledger.service.client;


import com.cloud.accountsledger.entity.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "accounts")
public interface AccountsFeignClient {
    @GetMapping (path = "/transactions")
    public ResponseEntity<String> postAccount(@RequestBody Accounts accounts);

}
