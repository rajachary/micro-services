package com.cloud.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.*;


@Data @AllArgsConstructor
public class ResponseDto {

    private HttpStatus statusCode;

    private String statusMsg;
    
}
