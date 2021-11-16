package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;
import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeePlaceRepository extends JpaRepository<CoffeePlaceEntity, Long> {

    Optional<CoffeePlaceEntity> findByName(String name);

    boolean existsByName(String name);

}
