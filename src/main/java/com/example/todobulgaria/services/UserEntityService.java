package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface UserEntityService {

    Optional<UserEntity> findUserByUsername(String username);
    UserEntity registrarUser(UserRegisterServiceModel userRegisterServiceModel) throws IOException;

    boolean existByUsername(String username);

    void updateUser(UserEntity userByUsername);

}





