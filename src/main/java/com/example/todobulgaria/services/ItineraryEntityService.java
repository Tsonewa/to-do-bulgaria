package com.example.todobulgaria.services;

import com.example.todobulgaria.models.dto.ItineraryDto;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.models.service.ItineryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinariesDetailsViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItineraryEntityService {

    void saveItinerary(ItineraryEntity itinerary);

    List<ItineraryDto> getAllItinerariesByTripId(Long trip_id);

    ItinariesDetailsViewModel findById(Long id);

    void updateItinerary(ItineryUpdateServiceModel itineraryUpdateServiceModel);

    ItinariesDetailsViewModel findByTripIdAndDay(Long id, Integer day);
}



