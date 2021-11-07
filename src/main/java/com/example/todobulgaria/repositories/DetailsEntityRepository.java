package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.DetailsEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsEntityRepository extends JpaRepository<DetailsEntity, Long> {
}
