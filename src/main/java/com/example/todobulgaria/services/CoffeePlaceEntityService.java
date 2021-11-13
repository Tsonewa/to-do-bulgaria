package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;

public interface CoffeePlaceEntityService {

    CoffeePlaceEntity findCoffeePlaceEntityByName(String name);

    void saveCoffeePlace(CoffeePlaceEntity newCoffeePlace);
}
