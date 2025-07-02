package com.walletapp.ewallet.service.serviceImpl;

import com.walletapp.ewallet.payload.LoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private String secretKey = null;
    public String generateToken(LoginDTO loginDTO) {
        Map<String, Object> claims = new HashMap<>();
          return  Jwts
                  .builder()
                  .claims()
                  .putAll(claims)
                  .subject(loginDTO.getUsername())
                  .issuer("DCA")
                  .issuedAt(new Date(System.currentTimeMillis()))
                  .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                  .and()
                  .signWith(generateKey())
                  .compact();
    }

    private SecretKey generateKey() {
        byte[] decode
                = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey(){
        return secretKey="aXc9kBC+ay6f1/suYPYhnS/T+TgqRfUfhFVdLhowxrA=";
    }
}
