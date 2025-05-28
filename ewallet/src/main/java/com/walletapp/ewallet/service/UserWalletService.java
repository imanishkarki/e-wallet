package com.walletapp.ewallet.service;

import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;

import java.math.BigDecimal;
import java.util.List;

public interface UserWalletService {
    ApiResponse createUserWalletDTO (UserWalletDTO userWalletDTO);
    ApiResponse loadUserWalletDTO (Long id, BigDecimal balanceToAdd);
    List<UserWalletDTO> getAllUserWalletDTO ();
    ApiResponse getUserWalletByIdDTO(Long id);
}
