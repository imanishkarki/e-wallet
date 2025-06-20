package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.IdNotFoundException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.repository.TransactionRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private UserWalletRepository userWalletRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ApiResponse createTransactionDTO(TransactionDTO transactionDTO) {
        Long senderId = transactionDTO.getSenderId();
        Long receiverId = transactionDTO.getReceiverId();
        BigDecimal amount = transactionDTO.getAmount();
        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("Sender and Receiver IDs must not be null");
        }
        if(amount  == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        UserWallet sender = userWalletRepository.findById(senderId)
                .orElseThrow(() -> new IdNotFoundException("Sender not found"));
        UserWallet receiver = userWalletRepository.findById(receiverId)
                .orElseThrow(() -> new IdNotFoundException("Receiver not found"));



        // Update balances
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        userWalletRepository.save(sender);
        userWalletRepository.save(receiver);

        // Create transaction
        Transaction transaction = Transaction.builder()
                .senderId(sender)
                .receiverId(receiver)
                .amount(amount)
                .status(StatusEnum.ACTIVE)
                .build();

        transactionRepository.save(transaction);

        return new ApiResponse(transactionDTO, true, "Transaction successful");
    }

}
