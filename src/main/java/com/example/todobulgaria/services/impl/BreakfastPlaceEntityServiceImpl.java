package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;
import com.example.todobulgaria.repositories.BreakfastPlaceRepository;
import com.example.todobulgaria.services.BreakfastPlaceEntityService;
import org.springframework.stereotype.Service;

@Service
public class BreakfastPlaceEntityServiceImpl implements BreakfastPlaceEntityService {

    private final BreakfastPlaceRepository breakfastPlaceRepository;

    public BreakfastPlaceEntityServiceImpl(BreakfastPlaceRepository breakfastPlaceRepository) {
        this.breakfastPlaceRepository = breakfastPlaceRepository;
    }


    @Override
    public BreakfastPlaceEntity findBreakfastPlaceEntityByName(String name) {

        return breakfastPlaceRepository.findByName(name).orElse(null);
    }

    @Override
    public BreakfastPlaceEntity saveBreakfastPlace(BreakfastPlaceEntity newBreakfastPlace) {

        return breakfastPlaceRepository.save(newBreakfastPlace);
    }

    @Override
    public boolean existBreakfastPlaceByName(String name) {
        return breakfastPlaceRepository.existsByName(name);
    }
}
