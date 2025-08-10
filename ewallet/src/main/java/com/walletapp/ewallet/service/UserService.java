package com.walletapp.ewallet.service;

import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.payload.SignupDTO;

public interface UserService {
    String verify(LoginDTO loginDTO);

    ApiResponse registerUser(SignupDTO signupDTO) throws WalletException;
}
