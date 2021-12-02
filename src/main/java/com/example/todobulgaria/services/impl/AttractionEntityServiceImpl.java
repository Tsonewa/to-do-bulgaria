package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.AttractionEntity;
import com.example.todobulgaria.repositories.AttractionsRepository;
import com.example.todobulgaria.services.AttractionEntityService;
import org.springframework.stereotype.Service;

@Service
public class AttractionEntityServiceImpl implements AttractionEntityService {

    private final AttractionsRepository attractionsRepository;

    public AttractionEntityServiceImpl(AttractionsRepository attractionsRepository) {
        this.attractionsRepository = attractionsRepository;
    }

    @Override
    public AttractionEntity findAttractionByName(String name) {
        return attractionsRepository.findByName(name).orElse(null);
    }

    @Override
    public boolean attractionExistByName(String name) {
        return attractionsRepository.existsByName(name);
    }

    @Override
    public AttractionEntity saveAttraction(AttractionEntity attraction) {
        return attractionsRepository.save(attraction);
    }
}
