package com.walletapp.ewallet.payload;

import com.walletapp.ewallet.entity.UserWallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
}
