package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ewallet")
public class UserWalletController {

    @Autowired
    private UserWalletService ewalletService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/load/{id}")
    public ResponseEntity<ApiResponse> loadUserWallet(@PathVariable Long id, @RequestBody BigDecimal balanceToAdd){
        return ResponseEntity.ok(ewalletService.loadUserWalletDTO(id, balanceToAdd));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserWalletById(@PathVariable Long id){
        return ResponseEntity.ok(ewalletService.deleteUserWalletById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserWalletDTO> getAllUserWallet(){
        return ewalletService.getAllUserWalletDTO();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserWalletByIdDTO(@PathVariable  Long id){
        return ResponseEntity.ok(ewalletService.getUserWalletByIdDTO(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActiveUserWalletsDTO() {
        return ResponseEntity.ok(ewalletService.getAllActiveUserWalletsDTO(StatusEnum.ACTIVE));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse> getInactiveUserWalletsDTO() {
        return ResponseEntity.ok(ewalletService.getAllInactiveUserWalletsDTO(StatusEnum.INACTIVE));
    }

    @GetMapping("/test")
    public String test(){
        return "Test endpoint is working!";
    }
}
