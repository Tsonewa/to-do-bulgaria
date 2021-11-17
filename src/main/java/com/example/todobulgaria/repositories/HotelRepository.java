package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import com.example.todobulgaria.models.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    Optional<HotelEntity> findByName(String name);

    boolean existsByName(String name);
}
