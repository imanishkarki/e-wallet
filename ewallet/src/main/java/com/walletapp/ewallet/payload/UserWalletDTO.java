package com.walletapp.ewallet.payload;

import lombok.*;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWalletDTO {
   // private Long id;
    private String name;
    private Long phoneNumber;
    private BigDecimal balance;

}
