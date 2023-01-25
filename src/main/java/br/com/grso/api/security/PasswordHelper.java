package br.com.grso.api.security;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelper {
    public PasswordEncoder getEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
