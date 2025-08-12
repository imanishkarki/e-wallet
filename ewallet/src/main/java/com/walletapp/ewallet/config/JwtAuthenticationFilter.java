package com.walletapp.ewallet.config;
import com.walletapp.ewallet.globalExceptionHandler.WalletException;
import com.walletapp.ewallet.service.CustomUserDetails;
import com.walletapp.ewallet.service.serviceImpl.CustomUserDetailsService;
import com.walletapp.ewallet.service.serviceImpl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;

        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String userName = jwtService.extractUsername(jwt);


            if (userName != null) {
                //Authenticate
                CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(userName);

                if (jwtService.isTokenValid(jwt, customUserDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            customUserDetails,
                            null,
                            customUserDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );
                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);

        }catch (Exception e){
            throw WalletException.builder()
                    .code("JWT001")
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .build();
        }
    }
}
