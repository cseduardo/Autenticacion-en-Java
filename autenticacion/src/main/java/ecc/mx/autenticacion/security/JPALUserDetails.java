package ecc.mx.autenticacion.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ecc.mx.autenticacion.models.LUser;

public class JPALUserDetails implements UserDetails {
    private final LUser users;
    public JPALUserDetails(LUser users) {
        this.users = users;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(users::getAuthority);
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }
    
}
