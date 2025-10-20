package ecc.mx.autenticacion.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import ecc.mx.autenticacion.service.JwtService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.Instant;

public class JwtServiceTest {
    JwtService jwtService = new JwtService();

    @BeforeEach
    public void prepararPruebas() {
        ReflectionTestUtils.setField(jwtService, "secretJwt",
                "becb67346c35b31037d45886f7e1e193245072555101ae0c006f03e3809b67b7");
    }

    @Test
    @Disabled
    public void testGenerateToken() {
        // Implementar prueba para la generación de JWT
    }

    @Test
    @Disabled
    public void testValidateToken() {
        // Implementar prueba para la validación de JWT
    }

    @Test
    @Disabled
    public void testExtractSpecificGrant() {
        // Implementar prueba para la extracción de claims del JWT
    }

    @Test
    public void testExtractUsername() {
        // GIVEN(Dado)
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJzdWIiOiJ0ZXN0R2lzZWxhIiwiSXNzdWVyIjoiSXNzdWVyIiwiVXNlcm5hbWUiOiJKYXZhSW5Vc2UiLCJleHAiOjE3OTI1MzEzNTUsImlhdCI6MTc2MDk5NTM1NX0.mU-Uo8zRQOa8QxuM5kTQsZY-s7I5SGuqqPIR1x2k7BA";
        // WHEN(Cuando)
        String username = jwtService.ExtractUsername(token);
        // assertTrue(username.equals("JavaInUse"));
        // THEN(Entonces)
        String expectedUsername = "testGisela";
        assertEquals(expectedUsername, username);
    }

    @Test
    public void testExtractExpiration() {
        // GIVEN(Dado)
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJzdWIiOiJ0ZXN0R2lzZWxhIiwiSXNzdWVyIjoiSXNzdWVyIiwiVXNlcm5hbWUiOiJKYXZhSW5Vc2UiLCJleHAiOjE3OTI1MzEzNTUsImlhdCI6MTc2MDk5NTM1NX0.mU-Uo8zRQOa8QxuM5kTQsZY-s7I5SGuqqPIR1x2k7BA";
        // WHEN(Cuando)
        Long expiration = jwtService.ExtractExpiration(token).getTime();
        // THEN(Entonces)
        String dateExpiration = "2026-10-20T21:22:35.616Z";

        // Parse the ISO 8601 string into an Instant
        Instant instant = Instant.parse(dateExpiration);
        
        Long timestampSeconds = instant.getEpochSecond();

        Long expirationTime = timestampSeconds * 1000;
        assertEquals(expirationTime, expiration);
    }

    @Test
    @Disabled
    public void testTokenHasExpired() {
        // Implementar prueba para la expiración del JWT
    }

    @Test
    @Disabled
    public void testIsTokenValid() {
        // Implementar prueba para la validez del JWT
    }
}
