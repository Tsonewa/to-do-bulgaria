package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.DinnerPlaceEntity;
import com.example.todobulgaria.repositories.DinnerPlaceRepository;
import com.example.todobulgaria.services.DinnerPlaceEntityService;
import org.springframework.stereotype.Service;

@Service
public class DinnerPlaceEntityServiceImpl implements DinnerPlaceEntityService {

   private final DinnerPlaceRepository dinnerPlaceRepository;

    public DinnerPlaceEntityServiceImpl(DinnerPlaceRepository dinnerPlaceRepository) {
        this.dinnerPlaceRepository = dinnerPlaceRepository;
    }


    @Override
    public DinnerPlaceEntity findDinnerPlaceEntityByName(String name) {
        return dinnerPlaceRepository.findByName(name).orElse(null);
    }

    @Override
    public DinnerPlaceEntity saveDinnerPlace(DinnerPlaceEntity newDinnerPlace) {

        return dinnerPlaceRepository.save(newDinnerPlace);
    }

    @Override
    public boolean existDinnerPlaceByName(String name) {
        return dinnerPlaceRepository.existsByName(name);
    }
}
