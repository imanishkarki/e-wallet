package com.walletapp.ewallet.entity;
import com.walletapp.ewallet.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="users")
public class User //implements UserDetails
 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;
    private String name;
    @Column(unique = true)
    private Long phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> role;

    private BigDecimal balance = BigDecimal.ZERO;
    //private String email;
    private String password;
    @Column(unique= true)
    private String username;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserWallet userWallet;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return List.of(new SimpleGrantedAuthority("ROLE"+ this.role.name()));
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
}
