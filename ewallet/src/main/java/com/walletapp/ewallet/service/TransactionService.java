package com.walletapp.ewallet.service;


import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;

import java.util.List;

public interface TransactionService {
    ApiResponse createTransactionDTO(TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactionByIdDTO(Long id);

    ApiResponse getTransactionStatement();
}
