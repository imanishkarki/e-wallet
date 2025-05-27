package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.repository.EwalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.walletapp.ewallet.service.EwalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EwalletServiceImpl implements EwalletService {


    @Autowired
    private EwalletRepository ewalletRepository;

    @Override
    public ApiResponse createUserWalletDTO (UserWalletDTO userWalletDTO){
        UserWallet uw = new UserWallet();

        uw.setName (userWalletDTO.getName());
        uw.setPhoneNumber(userWalletDTO.getPhoneNumber());
        uw.setBalance(userWalletDTO.getBalance()!= null? userWalletDTO.getBalance(): new BigDecimal("1000"));
        ewalletRepository.save(uw);
        UserWalletDTO uwd = new UserWalletDTO(uw.getId(),uw.getName(), uw.getPhoneNumber(),uw.getBalance());
        return new ApiResponse(uwd, true, "saved successfully");
    }

    @Override
    public ApiResponse loadUserWalletDTO (Long id, BigDecimal balanceToAdd ){
        UserWallet existingData =ewalletRepository.findById(id).get();
        existingData.setBalance(existingData.getBalance().add(balanceToAdd));
        ewalletRepository.save(existingData);
        UserWalletDTO uwd  =new UserWalletDTO(existingData.getId(),existingData.getName(),existingData.getPhoneNumber(),existingData.getBalance());
        return new ApiResponse(uwd, true, "Balance loaded successfully");

    }
}

