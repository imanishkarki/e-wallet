package com.walletapp.ewallet.globalExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletException extends RuntimeException
{
    private String code;
    private String message;
    private HttpStatus status;

    @Override
    public String getMessage() {
        return message;
    }
}
