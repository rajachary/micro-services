package com.cloud.accountsledger.exceptions;

import java.util.*;

public record ErrorResponse(
        String timestamp,
        int status,
        String message,
        Map<String, String> errors) {
}