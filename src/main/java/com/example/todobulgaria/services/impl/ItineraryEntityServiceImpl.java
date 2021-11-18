package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.dto.ItineraryDto;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.service.ItineryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinariesDetailsViewModel;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItineraryEntityServiceImpl implements ItineraryEntityService {

    private final ItineraryRepository itineraryRepository;
    private final ModelMapper modelMapper;
    private final BreakfastPlaceEntityService breakfastPlaceEntityService;
    private final DinnerPlaceEntityService dinnerPlaceEntityService;
    private final HotelEntityService hotelEntityService;
    private final CoffeePlaceEntityService coffeePlaceEntityService;
    private final AttractionEntityService attractionEntityService;

    public ItineraryEntityServiceImpl(ItineraryRepository itineraryRepository, ModelMapper modelMapper, BreakfastPlaceEntityService breakfastPlaceEntityService, DinnerPlaceEntityService dinnerPlaceEntityService, HotelEntityService hotelEntityService, CoffeePlaceEntityService coffeePlaceEntityService, AttractionEntityService attractionEntityService) {
        this.itineraryRepository = itineraryRepository;
        this.modelMapper = modelMapper;
        this.breakfastPlaceEntityService = breakfastPlaceEntityService;
        this.dinnerPlaceEntityService = dinnerPlaceEntityService;
        this.hotelEntityService = hotelEntityService;
        this.coffeePlaceEntityService = coffeePlaceEntityService;
        this.attractionEntityService = attractionEntityService;
    }

    @Override
    public void saveItinerary(ItineraryEntity itinerary) {

        itineraryRepository.saveAndFlush(itinerary);
    }

    @Override
    public List<ItineraryDto> getAllItinerariesByTripId(Long trip_id) {

        List<ItineraryEntity> allByTripId = itineraryRepository.findAllByTripId(trip_id);

        return allByTripId.stream().map(i -> modelMapper.map(i, ItineraryDto.class)).collect(Collectors.toList());
    }

    @Override
    public ItinariesDetailsViewModel findById(Long id) {

        ItineraryEntity itineraryEntity = itineraryRepository.findById(id).orElse(null);

        ItinariesDetailsViewModel map = modelMapper.map(itineraryEntity, ItinariesDetailsViewModel.class);
map.setAttractionName(itineraryEntity.getAttractions().get(0).getName());
map.setHotel(itineraryEntity.getHotelEntity().getName());
map.setDinnerPlace(itineraryEntity.getDinnerPlaceEntity().getName());
map.setCoffeePlace(itineraryEntity.getCoffeePlaceEntity().getName());
map.setBreakfastPlace(itineraryEntity.getBreakfastPlace().getName());

        return map;
    }

    @Override
    public void updateItinerary(ItineryUpdateServiceModel itineraryUpdateServiceModel) {

        ItineraryEntity byId = itineraryRepository.findById(itineraryUpdateServiceModel.getId()).orElseThrow();

        BreakfastPlaceEntity breakfastPlace = byId.getBreakfastPlace();
                breakfastPlace.setAddress(itineraryUpdateServiceModel
                        .getBrekfastPlaceAddress());

        breakfastPlace.setBookingUrl(itineraryUpdateServiceModel
                        .getBreakfastPlaceUrl());

        breakfastPlace
               .setName(itineraryUpdateServiceModel
                        .getBreakfastPlace());

        breakfastPlaceEntityService.saveBreakfastPlace(breakfastPlace);

        CoffeePlaceEntity coffeePlaceEntity = byId.getCoffeePlaceEntity();

        coffeePlaceEntity.setAddress(itineraryUpdateServiceModel.
                getAttractionAddress());

        coffeePlaceEntity.setBookingUrl(itineraryUpdateServiceModel
                        .getCoffeePlaceUrl());

        coffeePlaceEntity.setName(itineraryUpdateServiceModel
                        .getCoffeePlace());

        coffeePlaceEntityService.saveCoffeePlace(coffeePlaceEntity);

        DinnerPlaceEntity dinnerPlaceEntity = byId.getDinnerPlaceEntity();

        dinnerPlaceEntity.setAddress(itineraryUpdateServiceModel
                        .getDinnerPlaceAddress());

        dinnerPlaceEntity.setBookingUrl(itineraryUpdateServiceModel
                        .getDinnerPlaceUrl());

        dinnerPlaceEntity.setName(itineraryUpdateServiceModel
                        .getDinnerPlace());

        dinnerPlaceEntityService.saveDinnerPlace(dinnerPlaceEntity);

        HotelEntity hotelEntity = byId.getHotelEntity();

        hotelEntity.setAddress(itineraryUpdateServiceModel
                        .getHotelAddress());

             hotelEntity.setBookingUrl(itineraryUpdateServiceModel
                        .getHotelUrl());

             hotelEntity.setName(itineraryUpdateServiceModel
                        .getHotel());

             hotelEntityService.saveHotel(hotelEntity);

        itineraryRepository.save(byId);
    }

    @Override
    public ItinariesDetailsViewModel findByTripIdAndDay(Long id, Integer day) {
        ItineraryEntity byTripIdAndDay = itineraryRepository.findByTripIdAndDay(id, day);

        ItinariesDetailsViewModel map = modelMapper.map(byTripIdAndDay, ItinariesDetailsViewModel.class);

        map.setAttractionName(byTripIdAndDay.getAttractions().get(0).getName());
        map.setHotel(byTripIdAndDay.getHotelEntity().getName());
        map.setDinnerPlace(byTripIdAndDay.getDinnerPlaceEntity().getName());
        map.setCoffeePlace(byTripIdAndDay.getCoffeePlaceEntity().getName());
        map.setBreakfastPlace(byTripIdAndDay.getBreakfastPlace().getName());
        return map;
    }
}
