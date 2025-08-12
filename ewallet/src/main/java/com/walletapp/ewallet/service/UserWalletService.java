package com.walletapp.ewallet.service;

import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface UserWalletService {
    ApiResponse loadUserWalletDTO (Long id, BigDecimal balanceToAdd);
    List<UserWalletDTO> getAllUserWalletDTO ();
    ApiResponse getUserWalletByIdDTO(Long id);
    ApiResponse deleteUserWalletById(Long id);
    ApiResponse getAllActiveUserWalletsDTO(StatusEnum status);
    ApiResponse getAllInactiveUserWalletsDTO(StatusEnum status);
}
