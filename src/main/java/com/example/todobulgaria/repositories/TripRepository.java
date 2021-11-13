package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {

    @Query(nativeQuery = true, value = "select * from trips as t order by t.rating DESC LIMIT 8")
     List<TripEntity> findBestEightTripsOrderByRating();

    Optional<List<TripEntity>> findAllByUserId(Long id);
}
