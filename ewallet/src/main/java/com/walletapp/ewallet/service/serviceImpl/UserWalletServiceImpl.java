package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.DuplicateUserException;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.repository.TransactionRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.walletapp.ewallet.service.UserWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    @Autowired
    private UserWalletRepository ewalletRepository;
    @Autowired
    private UserWalletRepository userWalletRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ApiResponse createUserWalletDTO(UserWalletDTO userWalletDTO) {
        if(userWalletRepository.findByPhoneNumber(userWalletDTO.getPhoneNumber()) != null) {
            throw new DuplicateUserException("User wallet with this phone number already exists");
        }
        UserWallet uw = new UserWallet();
        uw.setName(userWalletDTO.getName());
        uw.setPhoneNumber(userWalletDTO.getPhoneNumber());
        uw.setStatus(StatusEnum.ACTIVE);
        uw.setBalance(BigDecimal.ZERO);
        ewalletRepository.save(uw);
        UserWalletDTO uwd = new UserWalletDTO(uw.getName(), uw.getPhoneNumber(), uw.getBalance());
        return new ApiResponse(uwd, true, "saved successfully");
    }

    @Override
    public ApiResponse loadUserWalletDTO(Long id, BigDecimal balanceToAdd) {

        UserWallet existingData = ewalletRepository.findById(id)
                .orElseThrow(() -> WalletException.builder()
                        .code("IDE00")
                        .status(HttpStatus.NOT_FOUND)
                        .build());

        existingData.setBalance(existingData.getBalance().add(balanceToAdd));
        ewalletRepository.save(existingData);

        UserWalletDTO uwd = new UserWalletDTO(
                existingData.getName(),
                existingData.getPhoneNumber(),
                existingData.getBalance());
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
                item.getPhoneNumber(),
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
                ew.getPhoneNumber(),
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
                data.getPhoneNumber(),
                data.getBalance()
        );
        data.setStatus(StatusEnum.INACTIVE);
        userWalletRepository.save(data);
        return new ApiResponse(dataDto, true, "User wallet deleted successfully");
    }

    @Override
    public ApiResponse getAllActiveUserWalletsDTO(StatusEnum status) {
        List<UserWallet> activeUserWallets= userWalletRepository.findByStatus(StatusEnum.ACTIVE);
        List<UserWalletDTO> activeUserWalletsDTO = activeUserWallets.stream()
                .map(uw -> new UserWalletDTO(
                        uw.getName(),
                        uw.getPhoneNumber(),
                        uw.getBalance()))
                .collect(Collectors.toList());
        return new ApiResponse(activeUserWalletsDTO, true, "All Active user wallets retrieved successfully");
    }


    @Override
    public ApiResponse getAllInactiveUserWalletsDTO(StatusEnum status) {
        List<UserWallet> activeUserWallets= userWalletRepository.findByStatus(StatusEnum.INACTIVE);
        List<UserWalletDTO> activeUserWalletsDTO = activeUserWallets.stream()
                .map(uw -> new UserWalletDTO(
                        uw.getName(),
                        uw.getPhoneNumber(),
                        uw.getBalance()))
                .collect(Collectors.toList());
        return new ApiResponse(activeUserWalletsDTO, true, "ALL Inactive user wallets retrieved successfully");
    }
}
