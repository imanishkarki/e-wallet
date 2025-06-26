package com.walletapp.ewallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;
    private String name;
    @Column(unique = true)
    private Long phoneNumber;
    private String email;
    private String password;

    @OneToOne(mappedBy = "user")
    private UserWallet userWallet;

}
