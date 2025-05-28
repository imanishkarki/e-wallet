package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ewallet")
public class UserWalletController {

    @Autowired
    private UserWalletService ewalletService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUserWalletDTO(@RequestBody UserWalletDTO userWalletDTO){
        return ResponseEntity.ok(ewalletService.createUserWalletDTO(userWalletDTO));
    }



    @PostMapping("/load/{id}")
    public ResponseEntity<ApiResponse> loadUserWallet(@PathVariable Long id, @RequestBody BigDecimal balanceToAdd){
        return ResponseEntity.ok(ewalletService.loadUserWalletDTO(id, balanceToAdd));
    }


    @GetMapping
    public List<UserWalletDTO> getAllUserWallet(){
        return ewalletService.getAllUserWalletDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserWalletByIdDTO(@PathVariable  Long id){
        return ResponseEntity.ok(ewalletService.getUserWalletByIdDTO(id));
    }
}
