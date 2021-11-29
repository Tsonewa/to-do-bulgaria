package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u LEFT JOIN FETCH u.roles where u.username = :username")
    Optional<UserEntity> findUserEntityByUsername(@Param("username") String username);

    boolean existsByUsername(String username);
}


