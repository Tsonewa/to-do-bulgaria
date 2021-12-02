package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.models.service.ItineraryUpdateServiceModel;

public interface ItineraryEntityService {

    ItineraryEntity saveItinerary(ItineraryEntity itinerary);

    ItineraryUpdateServiceModel findById(Long id);

    ItineraryEntity updateItinerary(ItineraryUpdateServiceModel itineraryUpdateServiceModel);

}




