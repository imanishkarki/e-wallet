package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.repository.UserRepository;
import com.walletapp.ewallet.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepositiry;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
//        Optional<User> userOptional = userRepository.findByUsername(loginDTO.getUsername());
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.status(401).body("Invalid username or password");
//        }
//        User user = userOptional.get();
//        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(401).body("Invalid username or password");
//        }
        return userService.verify(loginDTO);


        //return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request){
       return (CsrfToken) request.getAttribute("_csrf");
    }


}
