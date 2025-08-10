package com.walletapp.ewallet.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResp {
    private String message;
    private String errorCode;
//    private HttpStatus status;
    private boolean success;

}
