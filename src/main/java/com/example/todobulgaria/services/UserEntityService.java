package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;

import java.io.IOException;
import java.util.Optional;

public interface UserEntityService {
    void initUsers();

    Optional<UserEntity> findUserByUsername(String username);
    void registrarUser(UserRegisterServiceModel userRegisterServiceModel) throws IOException;
    Optional<UserEntity> findUserByEmail(String email);
    boolean existByUsername(String username);

}



