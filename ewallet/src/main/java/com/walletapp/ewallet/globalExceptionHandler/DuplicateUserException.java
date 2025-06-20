package com.walletapp.ewallet.globalExceptionHandler;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateUserException extends DuplicateKeyException {
    public DuplicateUserException(String message){
        super(message);
    }

}
