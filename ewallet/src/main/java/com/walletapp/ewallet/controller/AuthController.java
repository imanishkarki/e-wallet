package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.repository.UserRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;



@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserWalletRepository userWalletRepository;


//    @PostMapping("/signup")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return ResponseEntity.ok("User registered successfully");
//
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//
//        // Save user
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user); // ⬅️ use returned user
//
//        // Create wallet
//        UserWallet wallet = new UserWallet();
//        wallet.setName(savedUser.getUsername());
//        wallet.setPhoneNumber(null);
//        wallet.setBalance(BigDecimal.ZERO);
//        wallet.setStatus(StatusEnum.ACTIVE);
//        wallet.setUser(savedUser);
//
//        userWalletRepository.save(wallet);
//
//        return ResponseEntity.ok("User registered successfully");
//    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Save user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Create wallet using builder
        UserWallet wallet = UserWallet.builder()
                .user(savedUser)
                .name(savedUser.getUsername())
                //.id(savedUser.getId()) // Ensure the wallet ID matches the user ID
                ///.name(savedUser.getUsername())
                .phoneNumber(null)
                .balance(BigDecimal.ZERO)
                .status(StatusEnum.ACTIVE)
                .build();

        userWalletRepository.save(wallet);

        return ResponseEntity.ok("User registered successfully");
    }




    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return userService.verify(loginDTO);
    }

}
