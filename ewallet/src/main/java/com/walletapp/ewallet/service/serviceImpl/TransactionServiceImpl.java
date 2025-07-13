package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.IdNotFoundException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.repository.TransactionRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.CustomUserDetails;
import com.walletapp.ewallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private UserWalletRepository userWalletRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ApiResponse createTransactionDTO(TransactionDTO transactionDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User loggedInUser = userDetails.getUser();
        Long loggedInWalletId = loggedInUser.getUserWallet().getId();

        Long senderId = transactionDTO.getSenderId();
        Long receiverId = transactionDTO.getReceiverId();
        BigDecimal amount = transactionDTO.getAmount();

        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("Sender and Receiver IDs must not be null");
        }

        if (!loggedInWalletId.equals(senderId)) {
            throw new SecurityException("You are not authorized to initiate this transaction");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        UserWallet sender = userWalletRepository.findById(senderId)
                .orElseThrow(() -> new IdNotFoundException("Sender not found"));
        UserWallet receiver = userWalletRepository.findById(receiverId)
                .orElseThrow(() -> new IdNotFoundException("Receiver not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
        userWalletRepository.save(sender);
        userWalletRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .senderId(sender)
                .receiverId(receiver)
                .amount(amount)
                .status(StatusEnum.ACTIVE)
                .build();

        transactionRepository.save(transaction);

        return new ApiResponse(transactionDTO, true, "Transaction successful");
    }



//    @Override
//    public ApiResponse createTransactionDTO(TransactionDTO transactionDTO) {
//        Long senderId = transactionDTO.getSenderId();
//        Long receiverId = transactionDTO.getReceiverId();
//        BigDecimal amount = transactionDTO.getAmount();
//        if (senderId == null || receiverId == null) {
//            throw new IllegalArgumentException("Sender and Receiver IDs must not be null");
//        }
//        if(amount  == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than zero");
//        }
//        UserWallet sender = userWalletRepository.findById(senderId)
//                .orElseThrow(() -> new IdNotFoundException("Sender not found"));
//        UserWallet receiver = userWalletRepository.findById(receiverId)
//                .orElseThrow(() -> new IdNotFoundException("Receiver not found"));
//
//        // Update balances
//        if (sender.getBalance().compareTo(amount) < 0) {
//            throw new RuntimeException("Insufficient balance");
//        }
//
//        sender.setBalance(sender.getBalance().subtract(amount));
//        receiver.setBalance(receiver.getBalance().add(amount));
//        userWalletRepository.save(sender);
//        userWalletRepository.save(receiver);
//
//        // Create transaction
//        Transaction transaction = Transaction.builder()
//                .senderId(sender)
//                .receiverId(receiver)
//                .amount(amount)
//                .status(StatusEnum.ACTIVE)
//                .build();
//
//        transactionRepository.save(transaction);
//
//        return new ApiResponse(transactionDTO, true, "Transaction successful");
//    }

    @Override
    public List<TransactionDTO> getTransactionByIdDTO(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user= userDetails.getUser();

        UserWallet userWallet = userWalletRepository.findByUser(user)
                .orElseThrow(() -> new IdNotFoundException("User not found"));

        List<Transaction> transactionHistoryById = transactionRepository.findAllBySenderId_IdOrReceiverId_Id(id,id);
        List<TransactionDTO> transactionByIdDTOList = transactionHistoryById.stream()
                .map(item -> new TransactionDTO(
                        item.getSenderId(),
                        item.getReceiverId(),
                        item.getAmount()))
                        .collect(Collectors.toList());
                return transactionByIdDTOList;

        }
    }


