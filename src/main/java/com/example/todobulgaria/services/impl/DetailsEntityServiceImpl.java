package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.DetailsEntity;
import com.example.todobulgaria.repositories.DetailsEntityRepository;
import com.example.todobulgaria.services.DetailsEntityService;
import org.springframework.stereotype.Service;

@Service
public class DetailsEntityServiceImpl implements DetailsEntityService {

    private final DetailsEntityRepository detailsEntityRepository;

    public DetailsEntityServiceImpl(DetailsEntityRepository detailsEntityRepository) {
        this.detailsEntityRepository = detailsEntityRepository;
    }


    @Override
    public DetailsEntity saveDetailsEntity(DetailsEntity detailsEntity) {

        return detailsEntityRepository.saveAndFlush(detailsEntity);
    }
}
