package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.TownEntity;

import java.util.Optional;

public interface TownEntityService {
    TownEntity findTownByName(String name);

    void saveTown(TownEntity newTown);
}

