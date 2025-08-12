package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.IdNotFoundException;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;
import com.walletapp.ewallet.repository.TransactionRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.CustomUserDetails;
import com.walletapp.ewallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Long senderId = loggedInUser.getUserWallet().getId();
        transactionDTO.setSenderId(senderId);
        Long receiverId = transactionDTO.getReceiverId();
        BigDecimal amount = transactionDTO.getAmount();

//        if (senderId == null || receiverId == null) {
//            throw new IllegalArgumentException("Sender and Receiver IDs must not be null");
//        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw  WalletException.builder()
                                    .code("IB01")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .build();
        }

        UserWallet sender = userWalletRepository.findById(senderId).get();


        UserWallet receiver = userWalletRepository.findById(receiverId)
                .orElseThrow(() ->  WalletException.builder()
                        .code("IDE02")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        if (sender.getBalance().compareTo(amount) < 0) {
            throw  WalletException.builder()
                    .code("IB00")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
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
                        item.getSenderId().getId(),
                        item.getReceiverId().getId(),
                        item.getAmount()))
                        .collect(Collectors.toList());
                return transactionByIdDTOList;
        }

    @Override
    public ApiResponse getTransactionStatement() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
//            return new ApiResponse(null, false, "User not authenticated");
//        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User loggedInUser = userDetails.getUser();
        UserWallet wallet = loggedInUser.getUserWallet();

        if (wallet == null) {
            return new ApiResponse(null, false, "User wallet not found");
        }
        List<Transaction> transactions = transactionRepository.findByWalletId(wallet.getId());
        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(t -> new TransactionDTO(
                        t.getSenderId().getId(),
                        t.getReceiverId().getId(),
                        t.getAmount()
                ))
                .toList();

        return new ApiResponse(transactionDTOs, true, "Transaction statement retrieved successfully");
    }
}

