package ecc.mx.autenticacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ecc.mx.autenticacion.models.Login;
import ecc.mx.autenticacion.security.JPAUserDetailsService;
import ecc.mx.autenticacion.service.JwtService;

@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JPAUserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody Login loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        return jwtService.GenerateToken(userDetails.getUsername());
    }
}
