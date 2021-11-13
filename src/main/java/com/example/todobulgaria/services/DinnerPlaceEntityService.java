package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.DinnerPlaceEntity;

public interface DinnerPlaceEntityService {

    DinnerPlaceEntity findDinnerPlaceEntityByName(String name);

    void saveDinnerPlace(DinnerPlaceEntity newDinnerPlace);
}
