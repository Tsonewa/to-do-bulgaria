package com.example.todobulgaria.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

public class UserService extends User {

    public UserService(String username, String password,
                       Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserService(String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);
    }


    public String getUserIdentifier() {
        return getUsername();
    }
}
