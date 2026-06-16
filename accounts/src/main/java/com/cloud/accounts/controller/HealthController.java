package com.cloud.accounts.controller;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.*;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "timestamp", Instant.now()
        );
    }
}