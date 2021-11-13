package com.example.todobulgaria.services;

import com.example.todobulgaria.models.dto.ItineraryDto;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.ItineraryEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItineraryEntityService {

    void saveItinerary(ItineraryEntity itinerary);

    List<ItineraryDto> getAllItinerariesByTripId(Long trip_id);

}

