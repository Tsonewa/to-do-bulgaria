package com.example.todobulgaria.repositories;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;
import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoffeePlaceRepository extends JpaRepository<CoffeePlaceEntity, Long> {

    Optional<CoffeePlaceEntity> findByName(String name);

    boolean existsByName(String name);

    @Modifying
    @Query("update CoffeePlaceEntity c set c.address = :address, c.bookingUrl = :bookingUrl where c.id = :id")
    void updateCoffeePlace(@Param(value = "id") long id, @Param(value = "address") String address, @Param(value = "bookingUrl") String bookingUrl);

}
