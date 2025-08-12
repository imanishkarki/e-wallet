package com.walletapp.ewallet.payload;
import com.walletapp.ewallet.enums.RoleEnum;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Name field cannot be empty")
    private String name;
    @NotBlank(message = "Username field cannot be empty")
    private String username;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotBlank(message = " Password field cannot be empty")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String password;
    @NotEmpty(message = "Enter the Role")
    private Set<RoleEnum> role;
    private BigDecimal balance = BigDecimal.ZERO;
}
