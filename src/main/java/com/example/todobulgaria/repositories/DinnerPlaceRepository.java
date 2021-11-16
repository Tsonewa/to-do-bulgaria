package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import com.example.todobulgaria.models.entities.DinnerPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DinnerPlaceRepository extends JpaRepository<DinnerPlaceEntity, Long> {

    Optional<DinnerPlaceEntity> findByName(String name);

    boolean existsByName(String name);

}
