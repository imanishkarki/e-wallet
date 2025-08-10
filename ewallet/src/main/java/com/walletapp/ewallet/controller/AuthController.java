package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.RoleEnum;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.payload.SignupDTO;
import com.walletapp.ewallet.repository.UserRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
//        user.setBalance(BigDecimal.ZERO); // Initialize balance to zero
//        User savedUser = userRepository.save(user);
//        if (savedUser.getRole().contains(RoleEnum.USER)) {
//            UserWallet wallet = UserWallet.builder()
//                    .user(savedUser)
//                    .name(savedUser.getUsername())
//                    .phoneNumber(null)
//                    .balance(BigDecimal.ZERO)
//                    .status(StatusEnum.ACTIVE)
//                    .build();
//            userWalletRepository.save(wallet);
//        }
//        return ResponseEntity.ok(user.getRole()+" User registered successfully");
//    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignupDTO signupDTO){
        return ResponseEntity.ok(userService.registerUser(signupDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return userService.verify(loginDTO);
    }
}
