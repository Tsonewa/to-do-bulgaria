package com.example.todobulgaria.security;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User with username" + username + " not found!"));


        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(UserEntity user) {

        List<SimpleGrantedAuthority> collect = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                .collect(Collectors.toList());

        return new User(
                user.getUsername(),
                user.getPassword(),
                collect
        );
    }
}
