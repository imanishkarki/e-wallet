package com.walletapp.ewallet.repository;

import com.walletapp.ewallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository <Transaction, Long> {
    List<Transaction> findAllBySenderId_IdOrReceiverId_Id(Long senderId, Long receiverId);

    @Query("SELECT t FROM Transaction t WHERE t.senderId.id = :walletId OR t.receiverId.id = :walletId")
    List<Transaction> findByWalletId(Long walletId);
}
