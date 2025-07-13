package com.walletapp.ewallet.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    LocalDateTime timestamp;
    String message;
    String errorDetails;
    String errorCode;
    Boolean success;
    public ErrorResponse( String message, String errorDetails, String errorCode, Boolean success) {
       this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errorDetails = errorDetails;
        this.errorCode = errorCode;
        this.success = success;
    }
}
