package ecc.mx.autenticacion.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    private String secretJwt="ba193e49ea979d48236c15cf63401280e696ebd05008da3187427b8a23dff4d6";

    SecretKey GenerateDigitalKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretJwt);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String GenerateToken(String username) {
        // Lógica para generar un token JWT usando la biblioteca de tu elección
        // Utiliza 'secretJwt' como la clave secreta para firmar el token
        Map<String, Object> concessions = new HashMap<>();

        return Jwts.builder().claims().add(concessions).and()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+1000*60*60))
        .signWith(GenerateDigitalKey())
        .compact();
    }

    private Claims ExtractGrant(String token) {
        return Jwts.parser()
        .verifyWith(GenerateDigitalKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public <T> T ExtractSpecificGrant(String token, Function<Claims, T> concesionResolver) {
        final Claims claims = ExtractGrant(token);
        return concesionResolver.apply(claims);
    }

    public String ExtractUsername(String token) {
        return ExtractSpecificGrant(token, Claims::getSubject);
    }

    public Date ExtractExpiration(String token) {
        return ExtractSpecificGrant(token, Claims::getExpiration);
    }

    public boolean TokenHasExpired(String token) {
        return ExtractExpiration(token).before(new Date());
    }

    public boolean IsTokenValid(String token, UserDetails userDetails) {
        final String usernameFromToken = ExtractUsername(token);
        return (usernameFromToken.equals(userDetails.getUsername()) && !TokenHasExpired(token));
    }
}
