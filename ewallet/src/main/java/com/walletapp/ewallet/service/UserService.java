package com.walletapp.ewallet.service;

import com.walletapp.ewallet.payload.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    String verify(LoginDTO loginDTO);
}
