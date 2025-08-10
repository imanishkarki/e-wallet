package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.RoleEnum;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.DuplicateUserException;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.LoginDTO;
import com.walletapp.ewallet.payload.SignupDTO;
import com.walletapp.ewallet.repository.UserRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserWalletRepository userWalletRepository;
    private final ErrorCodeService errorCodeService;
    public UserServiceImpl(
            PasswordEncoder passwordEncoder, UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService, UserWalletRepository userWalletRepository, ErrorCodeService errorCodeService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userWalletRepository = userWalletRepository;
        this.errorCodeService = errorCodeService;
    }


    @Override
    public String verify(LoginDTO loginDTO) {

        Authentication authenticate
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getUsername());
        }else{
            return "failure";
        }
    }


    @Override
    public ApiResponse registerUser(SignupDTO signupDTO) throws WalletException{
        if (userRepository.findByUsername(signupDTO.getUsername()).isPresent() ) {
            //throw new DuplicateUserException("Username already exists");
            throw  WalletException.builder()
                    .code("DUE00")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
           // return new ApiResponse(null,false, "Username already exist");
        }
        if (userRepository.findByPhoneNumber(signupDTO.getPhoneNumber()).isPresent()) {
            throw  WalletException.builder()
                    .code("DUE01")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
           // return new ApiResponse(null,false, "Phone number already registered");
          //  throw new DuplicateUserException("Phone number already registered");
        }
        signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        signupDTO.setBalance(BigDecimal.ZERO); // Initialize balance to zero
        User user = User.builder()
                .name(signupDTO.getName())
                .username(signupDTO.getUsername())
                .phoneNumber(signupDTO.getPhoneNumber())
                .password(signupDTO.getPassword())
                .role(signupDTO.getRole())
                .balance(signupDTO.getBalance())
                .build();
        User savedUser = userRepository.save(user);
        if (savedUser.getRole().contains(RoleEnum.USER)) {
            UserWallet wallet = UserWallet.builder()
                    .user(savedUser)
                    .name(savedUser.getUsername())
                    .phoneNumber(null)
                    .balance(BigDecimal.ZERO)
                    .status(StatusEnum.ACTIVE)
                    .build();
            userWalletRepository.save(wallet);
        }
       // return ResponseEntity.ok(user.getRole()+" User registered successfully");
        return new ApiResponse (null, true, "User registered successfull, "+ signupDTO.getName() + " is registered as " + signupDTO.getRole());
    }
}
