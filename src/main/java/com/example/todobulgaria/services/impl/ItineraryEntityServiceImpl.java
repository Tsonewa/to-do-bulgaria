package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.models.service.AddItineraryServiceModel;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.services.CategoryEntityService;
import com.example.todobulgaria.services.ItineraryEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ItineraryEntityServiceImpl implements ItineraryEntityService {

    private final ModelMapper modelMapper;
    private final ItineraryRepository itineraryRepository;
    private final CategoryEntityService categoryEntityService;

    public ItineraryEntityServiceImpl(ModelMapper modelMapper, ItineraryRepository itineraryRepository, CategoryEntityService categoryEntityService) {
        this.modelMapper = modelMapper;
        this.itineraryRepository = itineraryRepository;
        this.categoryEntityService = categoryEntityService;
    }

    @Override
    public void createItinerary(AddItineraryServiceModel addItineraryServiceModel) {

        ItineraryEntity entity = modelMapper.map(addItineraryServiceModel, ItineraryEntity.class);

//        entity.setCategoryEntity(categoryEntityService
//                .getCategoryByName(addItineraryServiceModel.getCategory()));
        
        entity.setCreatedOn(LocalDate.now());
        entity.setRating(0);

        itineraryRepository.save(entity);

    }
}
