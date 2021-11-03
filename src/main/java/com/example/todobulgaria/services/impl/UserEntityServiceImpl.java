package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.services.UserEntityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserEntityServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void initUsers() {

            if (userRepository.count() > 0) {
                return;
            }

                RoleEntity adminRole = roleRepository.findByRole(RoleEnum.ADMIN).orElse(null);
                RoleEntity userRole = roleRepository.findByRole(RoleEnum.USER).orElse(null);

                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setStatus(true);

                admin.setRoles(List.of(adminRole, userRole));
                userRepository.save(admin);

                UserEntity user = new UserEntity();
        user.setUsername("pesho");
        user.setPassword(passwordEncoder.encode("12345"));
        user.setStatus(true);

        user.setRoles(List.of(userRole));
                userRepository.save(user);
            }
    }
