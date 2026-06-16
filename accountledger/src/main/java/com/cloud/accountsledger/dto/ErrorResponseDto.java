package com.cloud.accountsledger.dto;

import lombok.*;

import java.time.*;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String errorMessage;
    private String errorCode;
    private String apiPath;
    private LocalDateTime errorTime;
}
