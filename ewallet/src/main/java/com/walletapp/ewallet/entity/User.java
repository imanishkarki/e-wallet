package com.walletapp.ewallet.entity;
import com.walletapp.ewallet.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="users")
public class User
 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;
    private String name;
    @Column(unique = true)
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> role;

 //private BigDecimal balance = BigDecimal.ZERO;
    private String password;
    @Column(unique= true)
    private String username;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserWallet userWallet;

}
