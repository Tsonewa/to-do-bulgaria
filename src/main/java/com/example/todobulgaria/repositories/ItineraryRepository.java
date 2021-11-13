package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {

    List<ItineraryEntity> findAllByTripId(Long trip_id);

}
