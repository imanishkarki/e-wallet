package com.walletapp.ewallet.controller;

import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserDTO;
import com.walletapp.ewallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody  UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));

    }

}
