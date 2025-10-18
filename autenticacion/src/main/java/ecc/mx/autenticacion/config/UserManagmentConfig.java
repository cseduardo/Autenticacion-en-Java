package ecc.mx.autenticacion.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserManagmentConfig {
    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException{
        SecureRandom s=SecureRandom.getInstanceStrong();
        return new BCryptPasswordEncoder(12,s);
    }
}
