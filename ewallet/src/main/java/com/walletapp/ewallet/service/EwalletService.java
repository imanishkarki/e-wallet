package com.walletapp.ewallet.service;

import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;

import java.math.BigDecimal;

public interface EwalletService {
    ApiResponse createUserWalletDTO (UserWalletDTO userWalletDTO);
    ApiResponse loadUserWalletDTO (Long id, BigDecimal balanceToAdd);
}
