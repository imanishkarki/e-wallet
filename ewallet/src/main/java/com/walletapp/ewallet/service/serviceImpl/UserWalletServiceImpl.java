package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.UserWalletDTO;
import com.walletapp.ewallet.repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.walletapp.ewallet.service.UserWalletService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWalletServiceImpl implements UserWalletService {


    @Autowired
    private UserWalletRepository ewalletRepository;

    @Override
    public ApiResponse createUserWalletDTO(UserWalletDTO userWalletDTO) {
        UserWallet uw = new UserWallet();

        uw.setName(userWalletDTO.getName());
        uw.setPhoneNumber(userWalletDTO.getPhoneNumber());
        uw.setBalance(userWalletDTO.getBalance() != null ? userWalletDTO.getBalance() : new BigDecimal("1000"));
        ewalletRepository.save(uw);
        UserWalletDTO uwd = new UserWalletDTO( uw.getName(), uw.getPhoneNumber(), uw.getBalance());
        return new ApiResponse(uwd, true, "saved successfully");
    }

    @Override
    public ApiResponse loadUserWalletDTO(Long id, BigDecimal balanceToAdd) {
        UserWallet existingData = ewalletRepository.findById(id).get();
        existingData.setBalance(existingData.getBalance().add(balanceToAdd));
        ewalletRepository.save(existingData);
        UserWalletDTO uwd = new UserWalletDTO( existingData.getName(), existingData.getPhoneNumber(), existingData.getBalance());
        return new ApiResponse(uwd, true, "Balance loaded successfully");

    }

    @Override
    public List<UserWalletDTO> getAllUserWalletDTO() {
        List<UserWallet> uwList = ewalletRepository.findAll();
        List<UserWalletDTO> dtoList = uwList.stream().map(item -> new UserWalletDTO(

                item.getName(),
                item.getPhoneNumber(),
                item.getBalance()
        )).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public ApiResponse getUserWalletByIdDTO(Long id) {

        UserWallet ew = ewalletRepository.findById(id).get();

        UserWalletDTO uwd = new UserWalletDTO(

                ew.getName(),
                ew.getPhoneNumber(),
                ew.getBalance()
        );
        return new ApiResponse(uwd , true, "Get by id success");
    }
}

