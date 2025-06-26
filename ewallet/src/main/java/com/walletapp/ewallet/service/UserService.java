package com.walletapp.ewallet.service;

import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserDTO;

public interface UserService {
    ApiResponse createUser(UserDTO userDTO);

}
