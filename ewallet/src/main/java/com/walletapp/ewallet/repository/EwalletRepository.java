package com.walletapp.ewallet.repository;

import com.walletapp.ewallet.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EwalletRepository extends JpaRepository<UserWallet, Long> {


}
