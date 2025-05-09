package com.walletapp.ewallet.controller;

import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.payload.UserDto;
import com.walletapp.ewallet.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    @Autowired
    private UserRepo userRepository;

    @GetMapping("/")
    public String helloWorld() {
        double a = 5.00;
        double b = Math.random();
        if (a == b) {
            return "fjsdflkj";
        } else {
            return "Hello World";

        }
    }
    @PostMapping("/enter")
    public String postUser(@RequestBody UserDto us){
        User newUser = new User();
        newUser.setUsername(us.getUsername());
         userRepository.save(newUser) ;
        return "Success";
    }
}
