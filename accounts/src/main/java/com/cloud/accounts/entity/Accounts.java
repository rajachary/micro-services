package com.cloud.accounts.entity;

import jakarta.persistence.*;

@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String accountId;
    Double balance;
    String currency;
}
