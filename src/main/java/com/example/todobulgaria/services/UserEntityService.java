package com.example.todobulgaria.services;

import com.example.todobulgaria.models.dto.UserRegistrationDto;
import com.example.todobulgaria.models.entities.UserEntity;

import java.util.Optional;

public interface UserEntityService {
    void initUsers();

    Optional<UserEntity> findUserByUsername(String username);
    UserEntity registrarUser(UserRegistrationDto registrationDto);
    Optional<UserEntity> findUserByEmail(String email);
    boolean existByUsername(String username);
}


