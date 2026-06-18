package com.cloud.accounts.exceptions;

import java.util.*;

/**
 * Standardized error response record for API error responses.
 * <p>
 * This record encapsulates error information including timestamp, HTTP status,
 * error message, and detailed field errors. It provides a consistent structure
 * for all error responses across the Account Ledger microservice.
 * </p>
 *
 * @param timestamp the ISO-8601 formatted timestamp when the error occurred
 * @param status the HTTP status code (e.g., 400, 404, 500)
 * @param message a human-readable error message describing the issue
 * @param errors a map of field names to specific error messages for validation failures
 */
public record ErrorResponse(
        String timestamp,
        int status,
        String message,
        Map<String, String> errors) {
}