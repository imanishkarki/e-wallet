package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public CustomUserDetails loadUserByUsername(String username)  {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> WalletException.builder()
                        .code("IDE04")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}