package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.HotelEntity;

public interface HotelEntityService {

    HotelEntity findHotelEntityByName(String name);

    boolean existHotelEntityByName(String name);
    HotelEntity saveHotel(HotelEntity newHotel);
}
