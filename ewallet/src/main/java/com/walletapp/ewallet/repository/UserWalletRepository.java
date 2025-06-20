package com.walletapp.ewallet.repository;

import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
    List<UserWallet> findByStatus(StatusEnum status);
    UserWallet findByPhoneNumber(Long phoneNumber);

}
