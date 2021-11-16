package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;

public interface BreakfastPlaceEntityService {

    BreakfastPlaceEntity findBrekfastPlaceEntityByName(String name);

    void saveBreakfastPlace(BreakfastPlaceEntity newBreakfastPlace);

    boolean existBreakfastPlaceByName(String name);
}

