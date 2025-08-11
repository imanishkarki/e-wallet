package com.walletapp.ewallet.controller;

import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.globalExceptionHandler.IdNotFoundException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;
import com.walletapp.ewallet.repository.TransactionRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.CustomUserDetails;
import com.walletapp.ewallet.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> createTransactionDTO(@Valid @RequestBody  TransactionDTO transactionDTO){

        return ResponseEntity.ok( transactionService.createTransactionDTO( transactionDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{id}")
    public List<TransactionDTO> getTransactionByIdDTO(@PathVariable Long id) {

        return transactionService.getTransactionByIdDTO(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/statement")
    public ResponseEntity<ApiResponse> getTransactionStatement(){
      //
        return ResponseEntity.ok(transactionService.getTransactionStatement());
    }

}
