package ecc.mx.autenticacion.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import ecc.mx.autenticacion.config.UserManagmentConfig;
import ecc.mx.autenticacion.models.LUser;
import ecc.mx.autenticacion.repository.UserRepository;
import ecc.mx.autenticacion.security.JPAUserDetailsService;
import ecc.mx.autenticacion.service.JwtService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@WebMvcTest(LoginController.class)
@Import({
    UserManagmentConfig.class,
    JPAUserDetailsService.class,
    JwtService.class
})
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void testLogin() throws Exception {
        LUser usuario = new LUser();
        usuario.setUsername("testuser");
        usuario.setPassword("$2a$10$mMt4KG8SLI7P66hddHB0lOcaU0ltg7NnWdIO70KwPiIsQdI0iSkRe");
        usuario.setAuthority("lectura");
        usuario.setEmail("test@example.com");
        usuario.setId(1L);
        Optional<LUser> optionalUser = Optional.of(usuario);

        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);

        mockMvc.perform(
                post("/login")
                        .accept(MediaType.ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"username\":\"testuser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void testLoginInvalidUser() throws Exception {
        Optional<LUser> optionalUser = Optional.empty();
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(optionalUser);

        mockMvc.perform(
                post("/login")
                        .accept(MediaType.ALL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"username\":\"invaliduser\",\"password\":\"password123\"}"))
                .andExpect(status().isUnauthorized());
    }
}
