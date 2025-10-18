package ecc.mx.autenticacion.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ecc.mx.autenticacion.models.LUser;
import ecc.mx.autenticacion.repository.UserRepository;

@Component
public class JPAUserDetailsService implements UserDetailsService {
    @Autowired UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LUser> user = userRepository.findByUsername(username);
        
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }else{
            return new JPALUserDetails(user.get());
        }
    }
}
