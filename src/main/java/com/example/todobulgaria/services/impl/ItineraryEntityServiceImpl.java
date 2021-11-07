package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.service.AddItineraryServiceModel;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.security.UserService;
import com.example.todobulgaria.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItineraryEntityServiceImpl implements ItineraryEntityService {

    private final ModelMapper modelMapper;
    private final ItineraryRepository itineraryRepository;
    private final CategoryEntityService categoryEntityService;
    private final TownEntityService townEntityService;
    private final AttractionEntityService attractionEntityService;
    private final DetailsEntityService detailsEntityService;
    private final UserEntityService userEntityService;

    public ItineraryEntityServiceImpl(ModelMapper modelMapper, ItineraryRepository itineraryRepository, CategoryEntityService categoryEntityService, TownEntityService townEntityService, AttractionEntityService attractionEntityService, DetailsEntityService detailsEntityService, UserEntityService userEntityService) {
        this.modelMapper = modelMapper;
        this.itineraryRepository = itineraryRepository;
        this.categoryEntityService = categoryEntityService;
        this.townEntityService = townEntityService;
        this.attractionEntityService = attractionEntityService;
        this.detailsEntityService = detailsEntityService;
        this.userEntityService = userEntityService;
    }

    @Override
    public void createItinerary(AddItineraryServiceModel addItineraryServiceModel) {

        ItineraryEntity entity = modelMapper.map(addItineraryServiceModel, ItineraryEntity.class);

        entity.setCategoryEntity(categoryEntityService
                .getCategoryByName(addItineraryServiceModel.getCategory()));

        entity.setCreatedOn(LocalDate.now());
        entity.setRating(0);

        if(townEntityService.findTownByName(addItineraryServiceModel.getTownName()) != null){
            entity.setTown(townEntityService.findTownByName(addItineraryServiceModel.getTownName()));
        }else {
            TownEntity newTown = new TownEntity();
            newTown.setName(addItineraryServiceModel.getTownName());
            newTown.setRegion(addItineraryServiceModel.getRegion());
            townEntityService.saveTown(newTown);
            entity.setTown(newTown);
        }

        List<AttractionEntity> attractions = addItineraryServiceModel.getAttractionsName()
                .stream()
                .map(a -> {
                    if (attractionEntityService.attractionExistByName(a)) {

                        return attractionEntityService.findAttractionByName(a);
                    } else {
                        AttractionEntity attraction = new AttractionEntity();
                        attraction.setName(a);

                        attractionEntityService.saveAttraction(attraction);

                        return attraction;
                    }

                }).collect(Collectors.toList());

        entity.setAttractions(attractions);

        DetailsEntity details =
                createDetailsEntity(addItineraryServiceModel.getEquipment(), addItineraryServiceModel.getFestivals(), addItineraryServiceModel.getFotoTip());

        detailsEntityService.saveDetailsEntity(details);

        entity.setDetails(details);

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        entity.setUser(userEntityService.findUserByUsername(principal.getUsername()).orElseThrow());

        //TODO implement adding pictures url using cloudinary -> use Pictures entity

        itineraryRepository.save(entity);

    }

    private DetailsEntity createDetailsEntity(String equipment, String festivals, String fotoTip) {

         DetailsEntity detailsEntity = new DetailsEntity();
         detailsEntity.setEquipment(equipment);
        detailsEntity.setFestivals(festivals);
        detailsEntity.setFotoTips(fotoTip);

        return detailsEntity;
    }
}
