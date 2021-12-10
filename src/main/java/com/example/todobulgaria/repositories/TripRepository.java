package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.TripEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {

    //Find all trips created by certain user
    List<TripEntity> findAllByUserId(Long id);

    //Find trips by start point and duration
    List<TripEntity> findAllByStartPointAndDuration(String startPoint, Integer duration);

    //Find trips by start point
    List<TripEntity> findAllByStartPoint(String startPoint);

    //Find trip by category and duration or start point, category and duration
    List<TripEntity>  findAllByStartPointAndDurationAndCategoryEntity_Id(String startPoint, Integer duration, Long categoryEntity_id);
}
