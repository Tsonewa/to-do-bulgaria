package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import com.example.todobulgaria.repositories.CoffeePlaceRepository;
import com.example.todobulgaria.services.CoffeePlaceEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CoffeePlaceEntityServiceImpl implements CoffeePlaceEntityService {

    private final CoffeePlaceRepository coffeePlaceRepository;

    public CoffeePlaceEntityServiceImpl(CoffeePlaceRepository coffeePlaceRepository) {
        this.coffeePlaceRepository = coffeePlaceRepository;
    }

    @Override
    public CoffeePlaceEntity findCoffeePlaceEntityByName(String name) {

        return coffeePlaceRepository.findByName(name).orElse(null);
    }

    @Override
    public boolean existCoffeePlaceByName(String name) {
        return coffeePlaceRepository.existsByName(name);
    }


    @Override
    public CoffeePlaceEntity saveCoffeePlace(CoffeePlaceEntity newCoffeePlace) {

    return coffeePlaceRepository.save(newCoffeePlace);
    }
}
