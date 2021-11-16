package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreakfastPlaceRepository extends JpaRepository<BreakfastPlaceEntity, Long> {

    Optional<BreakfastPlaceEntity> findByName(String name);

    boolean existsByName(String name);
}

