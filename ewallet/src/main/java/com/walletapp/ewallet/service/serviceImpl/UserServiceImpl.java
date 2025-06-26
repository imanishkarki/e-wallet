package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserDTO;
import com.walletapp.ewallet.repository.UserRepositiry;
import com.walletapp.ewallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositiry userRepositiry;

    @Override
    public ApiResponse createUser(UserDTO userDTO) {
        return null;
    }
}
