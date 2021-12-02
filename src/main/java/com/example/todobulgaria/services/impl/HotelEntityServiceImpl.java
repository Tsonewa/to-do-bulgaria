package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.HotelEntity;
import com.example.todobulgaria.repositories.HotelRepository;
import com.example.todobulgaria.services.HotelEntityService;
import org.springframework.stereotype.Service;

@Service
public class HotelEntityServiceImpl implements HotelEntityService {

    private final HotelRepository hotelRepository;

    public HotelEntityServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public HotelEntity findHotelEntityByName(String name) {
        return hotelRepository.findByName(name).orElse(null);
    }

    @Override
    public boolean existHotelEntityByName(String name) {
        return hotelRepository.existsByName(name);
    }

    @Override
    public HotelEntity saveHotel(HotelEntity newHotel) {

        return hotelRepository.save(newHotel);
    }
}
