package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.AttractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttractionsRepository extends JpaRepository<AttractionEntity, Long> {

    Optional<AttractionEntity> findByName(String name);
    boolean existsByName(String name);


}
