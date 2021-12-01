package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {

    @Query(nativeQuery = true, value = "select * from trips as t order by t.rating DESC LIMIT 8")
     List<TripEntity> findBestEightTripsOrderByRating();

    List<TripEntity> findAllByUserId(@Param("id") Long id);

    @Query(value = "select * from trips t where t.start_point like %:keyword% and t.duration = :duration", nativeQuery = true)
    List<TripEntity> findByKeywordAndDuration(@Param("keyword") String keyword, @Param("duration") int duration);

    @Query(value = "select * from trips t where t.start_point like %:keyword%", nativeQuery = true)
    List<TripEntity> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from trips t where t.start_point like %:keyword% and t.duration= :duration and t.category_entity_id= :category", nativeQuery = true)
    List<TripEntity> findByKeywordDurationAndCategory(@Param("keyword") String keyword, @Param("duration") int duration, @Param("category") int category);
}
