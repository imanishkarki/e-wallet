package com.walletapp.ewallet.entity;

import com.walletapp.ewallet.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name= "sender_id", referencedColumnName = "id")
    private UserWallet senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id",referencedColumnName = "id")
    private UserWallet receiverId;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

}
