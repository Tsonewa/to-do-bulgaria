package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.services.ItineraryEntityService;
import org.springframework.stereotype.Service;

@Service
public class ItineraryEntityServiceImpl implements ItineraryEntityService {

    private final ItineraryRepository itineraryRepository;

    public ItineraryEntityServiceImpl(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }

    @Override
    public void saveItinerary(ItineraryEntity itinerary) {

        itineraryRepository.saveAndFlush(itinerary);
    }
}
