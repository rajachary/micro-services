package com.cloud.accountsledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.*;

@SpringBootApplication
@EnableFeignClients
public class AccountLedgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountLedgerApplication.class, args);
	}

}
