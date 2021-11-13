package com.example.todobulgaria.services;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import com.example.todobulgaria.models.entities.HotelEntity;

public interface HotelEntityService {

    HotelEntity findHotelEntityByName(String name);

    void saveHotel(HotelEntity newHotel);
}
