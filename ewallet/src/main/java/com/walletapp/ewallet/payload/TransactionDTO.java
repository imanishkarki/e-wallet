package com.walletapp.ewallet.payload;

import com.walletapp.ewallet.entity.UserWallet;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long receiverId;

    @NotNull
    private BigDecimal amount;
}
