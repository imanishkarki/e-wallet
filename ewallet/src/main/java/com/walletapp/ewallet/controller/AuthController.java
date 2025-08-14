package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.payload.SignupDTO;
import com.walletapp.ewallet.payload.UsernameUpdateRequestDTO;
import com.walletapp.ewallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupDTO signupDTO){
        return ResponseEntity.ok(userService.registerUser(signupDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.verify(loginDTO));
    }

    @PutMapping("/update/username")
    public ResponseEntity<ApiResponse> updateUsername(@RequestBody UsernameUpdateRequestDTO usernameUpdateRequestDTO) {
        return ResponseEntity.ok(userService.updateUsername(usernameUpdateRequestDTO));
    }
}
