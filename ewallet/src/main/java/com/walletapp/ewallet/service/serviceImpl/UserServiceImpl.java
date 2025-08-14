package com.walletapp.ewallet.service.serviceImpl;
import com.walletapp.ewallet.entity.User;
import com.walletapp.ewallet.entity.UserWallet;
import com.walletapp.ewallet.enums.RoleEnum;
import com.walletapp.ewallet.enums.StatusEnum;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.model.ApiResponse;
import com.walletapp.ewallet.payload.*;
import com.walletapp.ewallet.repository.UserRepository;
import com.walletapp.ewallet.repository.UserWalletRepository;
import com.walletapp.ewallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserServiceImpl(
                PasswordEncoder passwordEncoder,
                UserRepository userRepository,
                AuthenticationManager authenticationManager,
                JwtService jwtService,
                UserWalletRepository userWalletRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userWalletRepository = userWalletRepository;
    }

    @Override
    public ApiResponse verify(LoginDTO loginDTO) {

        Authentication authenticate
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            String jwtToken =  jwtService.generateToken(loginDTO.getUsername());
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
            String role = userDetails.getUser().getRole().toString();
            Long id = userDetails.getUser().getId();
            LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                    .id(id)
                    .role(role)
                    .jwtToken(jwtToken)

                    .build();
            return new ApiResponse(loginResponseDTO, true, "Logged in successfully");
        }else{
            throw WalletException.builder()
                    .code("IDE03")
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    @Override
    public ApiResponse registerUser(SignupDTO signupDTO) throws WalletException{
        if (userRepository.findByUsername(signupDTO.getUsername()).isPresent() ) {
            throw  WalletException.builder()
                    .code("DUE00")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (userRepository.findByPhoneNumber(signupDTO.getPhoneNumber()).isPresent()) {
            throw  WalletException.builder()
                    .code("DUE01")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        User user = User.builder()
                .name(signupDTO.getName())
                .username(signupDTO.getUsername())
                .phoneNumber(signupDTO.getPhoneNumber())
                .password(signupDTO.getPassword())
                .role(signupDTO.getRole())
                .build();
        User savedUser = userRepository.save(user);
        if (savedUser.getRole().contains(RoleEnum.USER)) {
            UserWallet wallet = UserWallet.builder()
                    .user(savedUser)
                    .name(savedUser.getUsername())
                    //.phoneNumber(null)
                    .balance(BigDecimal.ZERO)
                    .status(StatusEnum.ACTIVE)
                    .build();
            userWalletRepository.save(wallet);
        }
        return new ApiResponse (null, true, "User registered successfull, "+ signupDTO.getName() + " is registered as " + signupDTO.getRole());
    }

    @Override
    public ApiResponse updateUsername(UsernameUpdateRequestDTO usernameUpdateRequestDTO) throws WalletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
//            return new ApiResponse(null, false, "User not authenticated");
//        }
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User loggedInUser = userDetails.getUser();
        //UserWallet wallet = loggedInUser.getUserWallet();
        Long id = loggedInUser.getId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> WalletException.builder()
                        .code("IDE00")
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    String oldUsername = user.getUsername();
    String username = usernameUpdateRequestDTO.getUsername();

    if(username == null || username.isEmpty()) {
            throw WalletException.builder()
                    .code("DUE02")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
    } else if (userRepository.findByUsername(username).isPresent()) {
        throw WalletException.builder()
                .code("DUE00")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
        user.setUsername(username);
        userRepository.save(user);
        UsernameUpdateResponseDTO usernameUpdateResponseDTO =  UsernameUpdateResponseDTO.builder()
                .id(id)
                .oldUsername(oldUsername)
                .newUsername(username)
                .build();
        return new  ApiResponse(usernameUpdateResponseDTO,true,"Username is updated" );
    }
}
