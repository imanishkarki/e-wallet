package com.walletapp.ewallet.payload;
import com.walletapp.ewallet.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    private String name;
    private String username;
    private Long phoneNumber;
    private String password;
    private Set<RoleEnum> role;
    private BigDecimal balance = BigDecimal.ZERO;
}
