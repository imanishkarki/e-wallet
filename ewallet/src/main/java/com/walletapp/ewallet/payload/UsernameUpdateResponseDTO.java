package com.walletapp.ewallet.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameUpdateResponseDTO {
    private Long id;
    private String newUsername;
    private String oldUsername;
}
