package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import com.example.todobulgaria.models.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {
}
