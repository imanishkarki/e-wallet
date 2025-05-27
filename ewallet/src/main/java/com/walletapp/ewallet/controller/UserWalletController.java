package com.walletapp.ewallet.controller;

import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.service.EwalletService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ewallet")
public class UserWalletController {

    @Autowired
    private EwalletService ewalletService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUserWalletDTO(@RequestBody UserWalletDTO userWalletDTO){
        return ResponseEntity.ok(ewalletService.createUserWalletDTO(userWalletDTO));
    }

    @PostMapping("/load/{id}")
    public ResponseEntity<ApiResponse> loadUserWallet(@PathVariable Long id, @RequestBody BigDecimal balanceToAdd){
        return ResponseEntity.ok(ewalletService.loadUserWalletDTO(id, balanceToAdd));

    }

}
