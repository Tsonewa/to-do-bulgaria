package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.TownEntity;
import com.example.todobulgaria.repositories.TownRepository;
import com.example.todobulgaria.services.TownEntityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TownEntityServiceImpl implements TownEntityService {

    private final TownRepository townRepository;

    public TownEntityServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public TownEntity findTownByName(String name) {
        return townRepository.getTownEntityByName(name).orElse(null);
    }

    @Override
    public boolean existTownEntityByName(String name) {
        return townRepository.existsByName(name);
    }

    @Override
    public TownEntity saveTown(TownEntity newTown) {

       return townRepository.save(newTown);
    }

}
