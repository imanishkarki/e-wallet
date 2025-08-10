package com.walletapp.ewallet.service.serviceImpl;
import  org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeService {
    private final Environment environment;

    public ErrorCodeService(Environment environment) {
        this.environment = environment;
    }

    public String getMessage(String code){
        return environment.getProperty(code, "Unknown error");
    }

}
