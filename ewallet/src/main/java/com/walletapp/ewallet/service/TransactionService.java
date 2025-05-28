package com.walletapp.ewallet.service;


import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;

public interface TransactionService {
    ApiResponse createTransactionDTO(TransactionDTO transactionDTO);
}
