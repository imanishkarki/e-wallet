package com.walletapp.ewallet.entity;

import com.walletapp.ewallet.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  UserWallet {
    @Id
    private Long id;
    private String name;
    private Long phoneNumber;
    private BigDecimal balance;

    @OneToMany(mappedBy = "senderId")
    private List<Transaction> sentTransaction;
    @OneToMany(mappedBy = "receiverId")
    private List<Transaction> receivedTransaction;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private  User user;
}