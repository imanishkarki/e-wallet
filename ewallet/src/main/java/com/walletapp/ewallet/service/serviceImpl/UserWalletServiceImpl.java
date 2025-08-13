package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.Transaction;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.DuplicateUserException;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.LoadResponseDTO;
import com.walletapp.ewallet.payload.TransactionDTO;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.repository.TransactionRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.walletapp.ewallet.service.UserWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    private  final TransactionRepository transactionRepository;
    private final UserWalletRepository ewalletRepository;
    private final UserWalletRepository userWalletRepository;

    public UserWalletServiceImpl(TransactionRepository transactionRepository, UserWalletRepository ewalletRepository, UserWalletRepository userWalletRepository) {
        this.transactionRepository = transactionRepository;
        this.ewalletRepository = ewalletRepository;
        this.userWalletRepository = userWalletRepository;
    }

    @Override
    public ApiResponse loadUserWalletDTO(Long id, BigDecimal balanceToAdd) {

        UserWallet receiver = ewalletRepository.findById(id)
                .orElseThrow(() -> WalletException.builder()
                        .code("IDE00")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        UserWallet existingData = ewalletRepository.findById(id)
                .orElseThrow(() -> WalletException.builder()
                        .code("IDE00")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        existingData.setBalance(existingData.getBalance().add(balanceToAdd));
        ewalletRepository.save(existingData);

        Transaction transaction = Transaction.builder()
                .receiverId(receiver)
                .amount(balanceToAdd)
                .status(StatusEnum.ACTIVE)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);

         LoadResponseDTO uwd = new LoadResponseDTO(
                receiver.getName(),
                transaction.getAmount());
        return new ApiResponse(uwd, true, "Balance loaded successfully");
    }

    @Override
    public List<UserWalletDTO> getAllUserWalletDTO() {
        List<UserWallet> uwList = ewalletRepository.findAll();
        if(uwList == null || uwList.isEmpty()) {
            throw WalletException.builder()
                    .code("CDE00")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        List<UserWalletDTO> dtoList = uwList.stream().map(item -> new UserWalletDTO(
                item.getName(),
                item.getBalance()
        )).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public ApiResponse getUserWalletByIdDTO(Long id) {
//        if (!ewalletRepository.existsById(id)) {
//            throw new NoSuchElementException("User doesn't exist with this id");
//        }
        UserWallet ew = ewalletRepository.findById(id)
                .orElseThrow(() ->  WalletException.builder()
                        .code("IDE00")
                        .status(HttpStatus.NOT_FOUND)
                        .build());
        UserWalletDTO uwd = new UserWalletDTO(
                ew.getName(),
                ew.getBalance()
        );
        return new ApiResponse(uwd, true, "Get by id success");
    }

    @Override
    public ApiResponse deleteUserWalletById(Long id) {
        UserWallet data = userWalletRepository.findById(id)
                .orElseThrow(() -> WalletException.builder()
                        .code("IDE00")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        UserWalletDTO dataDto = new UserWalletDTO(
                data.getName(),
                data.getBalance()
        );
        data.setStatus(StatusEnum.INACTIVE);
        userWalletRepository.save(data);
        return new ApiResponse(dataDto, true, "User wallet deleted successfully");
    }

    @Override
    public ApiResponse getAllActiveUserWalletsDTO(StatusEnum status) {
        List<UserWallet> activeUserWallets= userWalletRepository.findByStatus(StatusEnum.ACTIVE);
        if (activeUserWallets == null || activeUserWallets.isEmpty()){
            throw WalletException.builder()
                    .code("CDE00")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        List<UserWalletDTO> activeUserWalletsDTO = activeUserWallets.stream()
                .map(uw -> new UserWalletDTO(
                        uw.getName(),
                        uw.getBalance()))
                .collect(Collectors.toList());
        return new ApiResponse(activeUserWalletsDTO, true, "All Active user wallets retrieved successfully");
    }


    @Override
    public ApiResponse getAllInactiveUserWalletsDTO(StatusEnum status) {
        List<UserWallet> inActiveUserWallets= userWalletRepository.findByStatus(StatusEnum.INACTIVE);
        if (inActiveUserWallets == null || inActiveUserWallets.isEmpty()){
            throw WalletException.builder()
                    .code("CDE00")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        List<UserWalletDTO> activeUserWalletsDTO = inActiveUserWallets.stream()
                .map(uw -> new UserWalletDTO(
                        uw.getName(),
                        uw.getBalance()))
                .collect(Collectors.toList());
        return new ApiResponse(activeUserWalletsDTO, true, "ALL Inactive user wallets retrieved successfully");
    }
}
