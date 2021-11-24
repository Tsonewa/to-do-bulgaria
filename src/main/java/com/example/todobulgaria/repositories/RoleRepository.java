package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.RoleEntity;
import com.example.todobulgaria.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

     Optional<RoleEntity> findByRole(RoleEnum role);
}
