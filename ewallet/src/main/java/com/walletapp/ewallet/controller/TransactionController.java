package com.walletapp.ewallet.controller;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.TransactionDTO;
import com.walletapp.ewallet.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> createTransactionDTO(@Valid @RequestBody  TransactionDTO transactionDTO){
        return ResponseEntity.ok( transactionService.createTransactionDTO( transactionDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{id}")
    public List<TransactionDTO> getTransactionByIdDTO(@PathVariable Long id) {
        return transactionService.getTransactionByIdDTO(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/statement")
    public ResponseEntity<ApiResponse> getTransactionStatement(){
        return ResponseEntity.ok(transactionService.getTransactionStatement());
    }

}
