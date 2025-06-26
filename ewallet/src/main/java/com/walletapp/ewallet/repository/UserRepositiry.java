package com.walletapp.ewallet.repository;

import org.springframework.security.authentication.jaas.JaasAuthenticationCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositiry extends JaasAuthenticationCallbackHandler {
}
