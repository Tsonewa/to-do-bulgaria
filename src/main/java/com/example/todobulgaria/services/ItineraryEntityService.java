package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.models.service.ItineraryUpdateServiceModel;

public interface ItineraryEntityService {

    void saveItinerary(ItineraryEntity itinerary);

    ItineraryUpdateServiceModel findById(Long id);

    void updateItinerary(ItineraryUpdateServiceModel itineraryUpdateServiceModel);

}




