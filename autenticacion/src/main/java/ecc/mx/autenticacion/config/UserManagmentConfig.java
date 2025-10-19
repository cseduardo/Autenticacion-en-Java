package ecc.mx.autenticacion.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ecc.mx.autenticacion.security.JwtFilter;

@Configuration
public class UserManagmentConfig {
    @Autowired
    private JwtFilter jwfilter;

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        SecureRandom s = SecureRandom.getInstanceStrong();
        return new BCryptPasswordEncoder(12, s);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(
                c -> c.requestMatchers("/login").permitAll()
                .requestMatchers("/hello").hasAuthority("read")
                .requestMatchers("/hola").hasAuthority("lectura")
                .anyRequest().denyAll())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) es agregado para indicar que no queremos crear una sesión que
                .addFilterBefore(jwfilter, UsernamePasswordAuthenticationFilter.class);//indica que queremos agregar el filtro JwtFilter a la cadena de filtros de nuestras peticiones a la API.

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
