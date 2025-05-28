package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.entity.UserWallet;
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
    public ApiResponse createTransactionDTO( TransactionDTO transactionDTO) {
        Transaction td = new Transaction();
        TransactionDTO tdto = new TransactionDTO();
        UserWallet uw = new UserWallet();
       // UserWalletDTO uwdto = new UserWalletDTO();
        Long rid = td.getReceiverId();
        Long sid = td.getSenderId();
        UserWallet suw = userWalletRepository.findById(sid).get();
        UserWallet ruw = userWalletRepository.findById(rid).get();
        BigDecimal rbln = suw.getBalance();
        BigDecimal sbln = ruw.getBalance();
        BigDecimal amt = tdto.getAmount();
        rbln = rbln.add(amt);
        sbln = sbln.subtract(amt);
        suw.setBalance(sbln);
        ruw.setBalance(rbln);
        TransactionDTO dtos = new TransactionDTO(tdto.getSenderId(),tdto.getReceiverId(), tdto.getAmount());
        return new ApiResponse(dtos,true, "transaction successfull");
    }
}
