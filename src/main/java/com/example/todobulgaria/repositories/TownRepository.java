package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<TownEntity, Long> {

    Optional<TownEntity> getTownEntityByName(String name);

    boolean existsByName(String name);
}
