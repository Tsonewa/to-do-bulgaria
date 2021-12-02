package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.DinnerPlaceEntity;

public interface DinnerPlaceEntityService {

    DinnerPlaceEntity findDinnerPlaceEntityByName(String name);

    DinnerPlaceEntity saveDinnerPlace(DinnerPlaceEntity newDinnerPlace);

    boolean existDinnerPlaceByName(String name);
}
