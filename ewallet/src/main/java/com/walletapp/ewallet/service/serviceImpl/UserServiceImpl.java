package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.repository.UserRepository;
import com.walletapp.ewallet.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder, UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String verify(LoginDTO loginDTO) {

        Authentication authenticate
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getUsername());
        }else{
            return "failure";
        }
    }
}
