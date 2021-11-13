package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import com.example.todobulgaria.repositories.CoffeePlaceRepository;
import com.example.todobulgaria.services.CoffeePlaceEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CoffeePlaceEntityServiceImpl implements CoffeePlaceEntityService {

    private final CoffeePlaceRepository coffeePlaceRepository;
    private final ModelMapper modelMapper;

    public CoffeePlaceEntityServiceImpl(CoffeePlaceRepository coffeePlaceRepository, ModelMapper modelMapper) {
        this.coffeePlaceRepository = coffeePlaceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CoffeePlaceEntity findCoffeePlaceEntityByName(String name) {

        return coffeePlaceRepository.findByName(name).orElse(null);
    }

    @Override
    public void saveCoffeePlace(CoffeePlaceEntity newCoffeePlace) {

        coffeePlaceRepository.save(newCoffeePlace);
    }
}
