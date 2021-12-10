package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;
import com.example.todobulgaria.models.views.TripCategoryTownDurationViewModel;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserEntityService {

    Optional<UserEntity> findUserByUsername(String username);
    UserEntity registrarUser(UserRegisterServiceModel userRegisterServiceModel) throws IOException;

    boolean existByUsername(String username);

    void updateUser(UserEntity userByUsername);

    Set<TripCategoryTownDurationViewModel> getTripCategoryTownDurationViewModels(UserEntity userEntity);
}





