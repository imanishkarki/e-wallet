package com.walletapp.ewallet.service;

import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.payload.SignupDTO;
import com.walletapp.ewallet.payload.UsernameUpdateRequestDTO;

public interface UserService {
    ApiResponse verify(LoginDTO loginDTO);

    ApiResponse registerUser(SignupDTO signupDTO) throws WalletException;

    ApiResponse updateUsername(UsernameUpdateRequestDTO usernameUpdateRequestDTO) throws WalletException;
}
