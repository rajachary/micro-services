package com.cloud.accounts.dto;

import lombok.*;
import org.springframework.http.*;

import java.time.*;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String errorMessage;
    private HttpStatus errorCode;
    private String apiPath;
    private LocalDateTime errorTime;
}
