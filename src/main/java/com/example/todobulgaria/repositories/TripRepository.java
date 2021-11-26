package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {

    @Query(nativeQuery = true, value = "select * from trips as t order by t.rating DESC LIMIT 8")
     List<TripEntity> findBestEightTripsOrderByRating();

    List<TripEntity> findAllByUserId(@Param("id") Long id);

    @Query(value = "select * from trips t where t.region like %:keyword%", nativeQuery = true)
    List<TripEntity> findByKeyword(@Param("keyword") String keyword);

}
