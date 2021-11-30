package com.example.todobulgaria.security;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

    private UserEntity testUser;
    private RoleEntity adminRole, userRole;

    private UserDetailsImpl serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {

        serviceToTest = new UserDetailsImpl(mockUserRepository);

        adminRole = new RoleEntity();
        adminRole.setRole(RoleEnum.ADMIN);

        userRole = new RoleEntity();
        userRole.setRole(RoleEnum.USER);

        testUser = new UserEntity();
        testUser.setUsername("pesho");
        testUser.setEmail("pesho@gmail.com");
        testUser.setPassword("12345");
        testUser.setRoles(List.of(adminRole, userRole));
    }

    @Test
    void testUserNotFound() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalid_username")
        );
    }

    @Test
    void testUserFound() {

        when(mockUserRepository.findUserEntityByUsername(testUser.getUsername())).
                thenReturn(Optional.of(testUser));

        UserDetails actual = serviceToTest.loadUserByUsername(testUser.getUsername());

        String expectedRoles = "ROLE_ADMIN, ROLE_USER";
        String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
                Collectors.joining(", "));

        assertEquals(actual.getUsername(), testUser.getUsername());
        assertEquals(expectedRoles, actualRoles);
    }
}