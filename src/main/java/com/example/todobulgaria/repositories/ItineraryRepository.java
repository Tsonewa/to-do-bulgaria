package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {

    List<ItineraryEntity> findAllByTripId(Long trip_id);

    Optional<ItineraryEntity> findById(Long id);

    ItineraryEntity findByTripIdAndDay(Long id, Integer day);

}
