package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.ItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {

    @Query("select t.itineraries from TripEntity t where t.id = :id")
    List<ItineraryEntity> findAllByTripId(@Param("id")Long trip_id);

    Optional<ItineraryEntity> findById(Long id);

    ItineraryEntity findByTripIdAndDay(Long id, Integer day);

}
