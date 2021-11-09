package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.BestTripsArticleViewModel;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripEntityServiceImpl implements TripEntityService {

    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;
    private final CategoryEntityService categoryEntityService;
    private final TownEntityService townEntityService;
    private final AttractionEntityService attractionEntityService;
    private final DetailsEntityService detailsEntityService;
    private final UserEntityService userEntityService;
    private final ItineraryEntityService itineraryEntityService;

    public TripEntityServiceImpl(ModelMapper modelMapper, TripRepository tripRepository, CategoryEntityService categoryEntityService, TownEntityService townEntityService, AttractionEntityService attractionEntityService, DetailsEntityService detailsEntityService, UserEntityService userEntityService, ItineraryEntityService itineraryEntityService) {
        this.modelMapper = modelMapper;
        this.tripRepository = tripRepository;
        this.categoryEntityService = categoryEntityService;
        this.townEntityService = townEntityService;
        this.attractionEntityService = attractionEntityService;
        this.detailsEntityService = detailsEntityService;
        this.userEntityService = userEntityService;
        this.itineraryEntityService = itineraryEntityService;
    }

    @Override
    public void createTrip(AddTripServiceModel addTripServiceModel) {

        TripEntity entity = modelMapper.map(addTripServiceModel, TripEntity.class);

        entity.setCategoryEntity(categoryEntityService
                .getCategoryByName(addTripServiceModel.getCategoryName()));

        List<ItineraryEntity> itineraries = new ArrayList<>();

        for (int i = 0; i < addTripServiceModel.getTownName().size(); i++) {

            ItineraryEntity itineraryEntity = new ItineraryEntity();
            itineraryEntity.setDay(i + 1);

            if (townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)) != null) {
                itineraryEntity.setTown(townEntityService.findTownByName(addTripServiceModel.getTownName().get(i)));
            } else {
                TownEntity newTown = new TownEntity();
                newTown.setName(addTripServiceModel.getTownName().get(i));
                newTown.setRegion(addTripServiceModel.getRegion());
                townEntityService.saveTown(newTown);
                itineraryEntity.setTown(newTown);
            }
            
            itineraryEntity.setCreatedOn(LocalDate.now());
            itineraryEntity.setBreakfastPlace(addTripServiceModel.getBreakfastPlace().get(i));
            itineraryEntity.setDinnerPlace(addTripServiceModel.getDinnerPlace().get(i));
            itineraryEntity.setCoffeePlace(addTripServiceModel.getCoffeePlace().get(i));
            itineraryEntity.setHotel(addTripServiceModel.getHotel().get(i));
            itineraryEntity.setTrip(entity);

            if (attractionEntityService.attractionExistByName(addTripServiceModel.getAttractionsName().get(i))) {

                itineraryEntity.setAttractions(List.of(attractionEntityService.findAttractionByName(addTripServiceModel.getAttractionsName().get(i))));
            } else {
                AttractionEntity attraction = new AttractionEntity();
                attraction.setName(addTripServiceModel.getAttractionsName().get(i));

                attractionEntityService.saveAttraction(attraction);
                itineraryEntity.setAttractions(List.of(attractionEntityService.findAttractionByName(attraction.getName())));
            }

            itineraryEntityService
                    .saveItinerary(itineraryEntity);

            itineraries.add(itineraryEntity);
        }

        entity.setItineraries(itineraries);
        entity.setDuration(itineraries.size());

        DetailsEntity details =
                createDetailsEntity(addTripServiceModel.getEquipment(), addTripServiceModel.getFestivals(), addTripServiceModel.getFotoTip());

        detailsEntityService.saveDetailsEntity(details);

        entity.setDetails(details);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        entity.setUser(userEntityService.findUserByUsername(principal.getUsername()).orElseThrow());

        //TODO implement adding pictures url using cloudinary -> use Pictures entity

        tripRepository.save(entity);
    }

    @Override
    public  List<BestTripsArticleViewModel> findFirstEightBestTripsOrderByRating() {

        List<TripEntity> bestEightTripsOrderByRating = tripRepository.findBestEightTripsOrderByRating();

        return bestEightTripsOrderByRating
                .stream()
                .map(b -> {
                    BestTripsArticleViewModel map = modelMapper.map(b, BestTripsArticleViewModel.class);
                    map.setUrl(b.getPicture().getUrl());

                    System.out.println(map);
                    return map;
                }).collect(Collectors.toList());
    }


    private DetailsEntity createDetailsEntity(String equipment, String festivals, String fotoTip) {

         DetailsEntity detailsEntity = new DetailsEntity();
         detailsEntity.setEquipment(equipment);
        detailsEntity.setFestivals(festivals);
        detailsEntity.setFotoTips(fotoTip);

        return detailsEntity;
    }
}
