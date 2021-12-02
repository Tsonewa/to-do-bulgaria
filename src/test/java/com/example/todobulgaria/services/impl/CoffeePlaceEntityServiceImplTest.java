package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.CoffeePlaceEntity;
import com.example.todobulgaria.models.entities.DinnerPlaceEntity;
import com.example.todobulgaria.repositories.CoffeePlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoffeePlaceEntityServiceImplTest {

    @Mock
    private CoffeePlaceRepository coffeePlaceRepositoryMock;

    @InjectMocks
    private CoffeePlaceEntityServiceImpl serviceToTest;

    private CoffeePlaceEntity coffeePlaceEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new CoffeePlaceEntityServiceImpl(coffeePlaceRepositoryMock);

        coffeePlaceEntityTest = new CoffeePlaceEntity();
        coffeePlaceEntityTest.setId(1L);
        coffeePlaceEntityTest.setAddress("address");
        coffeePlaceEntityTest.setName("name");
        coffeePlaceEntityTest.setBookingUrl("url");

    }

    @DisplayName("Find existing coffee place by name")
    @Test
    void findCoffeePlaceEntityByName() {

        when(coffeePlaceRepositoryMock.findByName(coffeePlaceEntityTest.getName())).thenReturn(Optional.of(coffeePlaceEntityTest));

        CoffeePlaceEntity coffeePlaceEntity = serviceToTest.findCoffeePlaceEntityByName(coffeePlaceEntityTest.getName());

        verify(coffeePlaceRepositoryMock).findByName(coffeePlaceEntityTest.getName());

        assertThat(coffeePlaceEntity).isNotNull();
    }

    @DisplayName("Not existing coffee place searched by name")
    @Test
    void findCoffeePlaceEntityByNameDoesntFind() {

        when(coffeePlaceRepositoryMock.findByName("invalid_name")).thenReturn(Optional.empty());

        CoffeePlaceEntity coffeePlaceEntity = serviceToTest.findCoffeePlaceEntityByName("invalid_name");

        verify(coffeePlaceRepositoryMock).findByName("invalid_name");

        assertThat(coffeePlaceEntity).isNull();
    }

    @DisplayName("Existing coffee place search by name")
    @Test
    void existCoffeePlaceByName() {

        when(coffeePlaceRepositoryMock.existsByName(coffeePlaceEntityTest.getName())).thenReturn(true);

        assertThat(serviceToTest.existCoffeePlaceByName(coffeePlaceEntityTest.getName())).isTrue();

        verify(coffeePlaceRepositoryMock).existsByName(coffeePlaceEntityTest.getName());
    }

    @DisplayName("Not existing coffee place search by name")
    @Test
    void existCoffeePlaceByNameDoesntExist() {

        when(coffeePlaceRepositoryMock.existsByName("invalid_name")).thenReturn(false);

        assertThat(serviceToTest.existCoffeePlaceByName("invalid_name")).isFalse();

        verify(coffeePlaceRepositoryMock).existsByName("invalid_name");
    }

    @DisplayName("Successfully create coffee place entity")
    @Test
    void saveCoffeePlace() {

        when(coffeePlaceRepositoryMock.save(any(CoffeePlaceEntity.class))).thenReturn(coffeePlaceEntityTest);

        CoffeePlaceEntity savedCoffeePlace = serviceToTest.saveCoffeePlace(coffeePlaceEntityTest);

        verify(coffeePlaceRepositoryMock).save(coffeePlaceEntityTest);

        assertThat(savedCoffeePlace).isNotNull();
    }
}