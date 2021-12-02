package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.service.ItineraryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinerariesDetailsViewModel;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ItineraryEntityServiceImpl implements ItineraryEntityService {

    private final ItineraryRepository itineraryRepository;
    private final ModelMapper modelMapper;
    private final BreakfastPlaceEntityService breakfastPlaceEntityService;
    private final DinnerPlaceEntityService dinnerPlaceEntityService;
    private final HotelEntityService hotelEntityService;
    private final CoffeePlaceEntityService coffeePlaceEntityService;

    public ItineraryEntityServiceImpl(ItineraryRepository itineraryRepository, ModelMapper modelMapper, BreakfastPlaceEntityService breakfastPlaceEntityService, DinnerPlaceEntityService dinnerPlaceEntityService, HotelEntityService hotelEntityService, CoffeePlaceEntityService coffeePlaceEntityService) {
        this.itineraryRepository = itineraryRepository;
        this.modelMapper = modelMapper;
        this.breakfastPlaceEntityService = breakfastPlaceEntityService;
        this.dinnerPlaceEntityService = dinnerPlaceEntityService;
        this.hotelEntityService = hotelEntityService;
        this.coffeePlaceEntityService = coffeePlaceEntityService;
    }

    @Override
    public ItineraryEntity saveItinerary(ItineraryEntity itinerary) {

        return itineraryRepository.saveAndFlush(itinerary);
    }

    @Override
    public ItineraryUpdateServiceModel findById(Long id) {

        ItineraryEntity itineraryEntity = itineraryRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));

        ItinerariesDetailsViewModel itinerariesDetailsViewModel =
                asItineraryDetailView(itineraryEntity);

        return modelMapper.map(itinerariesDetailsViewModel
                , ItineraryUpdateServiceModel.class);
    }

    private ItinerariesDetailsViewModel asItineraryDetailView(ItineraryEntity itineraryEntity) {

        ItinerariesDetailsViewModel map = modelMapper
                .map(itineraryEntity, ItinerariesDetailsViewModel.class);

        map.setAttractionName(itineraryEntity.getAttractions().get(0).getName());
        map.setHotel(itineraryEntity.getHotelEntity().getName());
        map.setDinnerPlace(itineraryEntity.getDinnerPlaceEntity().getName());
        map.setCoffeePlace(itineraryEntity.getCoffeePlaceEntity().getName());
        map.setBreakfastPlace(itineraryEntity.getBreakfastPlace().getName());

        return map;
    }

    @Override
    public ItineraryEntity updateItinerary(ItineraryUpdateServiceModel itineraryUpdateServiceModel) {

        ItineraryEntity byId = itineraryRepository.findById
                (itineraryUpdateServiceModel.getId())
                .orElseThrow(() -> new ObjectNotFoundException(itineraryUpdateServiceModel.getId()));

        BreakfastPlaceEntity breakfastPlace = byId.getBreakfastPlace();
        breakfastPlace.setAddress(itineraryUpdateServiceModel
                .getBreakfastPlaceAddress());

        breakfastPlace.setBookingUrl(itineraryUpdateServiceModel
                .getBreakfastPlaceBookingUrl());

        breakfastPlace
                .setName(itineraryUpdateServiceModel
                        .getBreakfastPlace());

        breakfastPlaceEntityService.saveBreakfastPlace(breakfastPlace);

        CoffeePlaceEntity coffeePlaceEntity = byId.getCoffeePlaceEntity();

        coffeePlaceEntity.setAddress(itineraryUpdateServiceModel.
                getDinnerPlaceAddress());

        coffeePlaceEntity.setBookingUrl(itineraryUpdateServiceModel
                .getCoffeePlaceBookingUrl());

        coffeePlaceEntity.setName(itineraryUpdateServiceModel
                .getCoffeePlace());

        coffeePlaceEntityService.saveCoffeePlace(coffeePlaceEntity);

        DinnerPlaceEntity dinnerPlaceEntity = byId.getDinnerPlaceEntity();

        dinnerPlaceEntity.setAddress(itineraryUpdateServiceModel
                .getDinnerPlaceAddress());

        dinnerPlaceEntity.setBookingUrl(itineraryUpdateServiceModel
                .getDinnerPlaceBookingUrl());

        dinnerPlaceEntity.setName(itineraryUpdateServiceModel
                .getDinnerPlace());

        dinnerPlaceEntityService.saveDinnerPlace(dinnerPlaceEntity);

        HotelEntity hotelEntity = byId.getHotelEntity();

        hotelEntity.setAddress(itineraryUpdateServiceModel
                .getHotelAddress());

        hotelEntity.setBookingUrl(itineraryUpdateServiceModel
                .getHotelBookingUrl());

        hotelEntity.setName(itineraryUpdateServiceModel
                .getHotel());

        hotelEntityService.saveHotel(hotelEntity);

      return itineraryRepository.save(byId);
    }
}