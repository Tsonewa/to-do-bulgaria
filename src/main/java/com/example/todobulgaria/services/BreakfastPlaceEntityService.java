package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;

public interface BreakfastPlaceEntityService {

    BreakfastPlaceEntity findBreakfastPlaceEntityByName(String name);

    BreakfastPlaceEntity saveBreakfastPlace(BreakfastPlaceEntity newBreakfastPlace);

    boolean existBreakfastPlaceByName(String name);
}

