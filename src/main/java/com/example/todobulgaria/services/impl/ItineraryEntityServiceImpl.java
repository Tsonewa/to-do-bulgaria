package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.dto.ItineraryDto;
import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.services.ItineraryEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.modelmapper.Converters.Collection.map;

@Service
public class ItineraryEntityServiceImpl implements ItineraryEntityService {

    private final ItineraryRepository itineraryRepository;
    private final ModelMapper modelMapper;

    public ItineraryEntityServiceImpl(ItineraryRepository itineraryRepository, ModelMapper modelMapper) {
        this.itineraryRepository = itineraryRepository;
        this.modelMapper = modelMapper;
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
}
