package com.cloud.accountsledger.service.client;

import com.cloud.accountsledger.entity.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

@Component
public class AccountFallBack implements  AccountsFeignClient {
    @Override
    public ResponseEntity<Accounts> postAccount(String accountId, Accounts accounts) {
        return null;
    }
}
