package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.DinnerPlaceEntity;
import com.example.todobulgaria.repositories.DinnerPlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DinnerPlaceEntityServiceImplTest {

    @Mock
    private DinnerPlaceRepository dinnerPlaceRepositoryMock;

    @InjectMocks
    private DinnerPlaceEntityServiceImpl serviceToTest;

    private DinnerPlaceEntity dinnerPlaceEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new DinnerPlaceEntityServiceImpl(dinnerPlaceRepositoryMock);

        dinnerPlaceEntityTest = new DinnerPlaceEntity();
        dinnerPlaceEntityTest.setId(1L);
        dinnerPlaceEntityTest.setAddress("address");
        dinnerPlaceEntityTest.setName("name");
        dinnerPlaceEntityTest.setBookingUrl("url");
    }

    @DisplayName("Find existing dinner place by name")
    @Test
    void findDinnerPlaceEntityByName() {

        when(dinnerPlaceRepositoryMock.findByName(dinnerPlaceEntityTest.getName())).thenReturn(Optional.of(dinnerPlaceEntityTest));

        DinnerPlaceEntity dinnerPlaceEntity = serviceToTest.findDinnerPlaceEntityByName(dinnerPlaceEntityTest.getName());

        verify(dinnerPlaceRepositoryMock).findByName(dinnerPlaceEntityTest.getName());

        assertThat(dinnerPlaceEntity).isNotNull();
    }

    @DisplayName("Not existing dinner place search by name")
    @Test
    void findDinnerPlaceEntityByNameDoesntExist() {

        when(dinnerPlaceRepositoryMock.findByName("invalid_name")).thenReturn(Optional.empty());

        DinnerPlaceEntity dinnerPlaceEntityByName = serviceToTest.findDinnerPlaceEntityByName("invalid_name");

        verify(dinnerPlaceRepositoryMock).findByName("invalid_name");

        assertThat(dinnerPlaceEntityByName).isNull();

    }

    @DisplayName("Exist dinner place by name")
    @Test
    void existDinnerPlaceByName() {

        when(dinnerPlaceRepositoryMock.existsByName(dinnerPlaceEntityTest.getName())).thenReturn(true);

        assertThat(serviceToTest.existDinnerPlaceByName(dinnerPlaceEntityTest.getName())).isTrue();

        verify(dinnerPlaceRepositoryMock).existsByName(dinnerPlaceEntityTest.getName());
    }

    @DisplayName("Dinner place does not exist by name")
    @Test
    void existDinnerPlaceByNameDoesNotExist() {

        when(dinnerPlaceRepositoryMock.existsByName("invalid_name")).thenReturn(false);

        assertThat(serviceToTest.existDinnerPlaceByName("invalid_name")).isFalse();

        verify(dinnerPlaceRepositoryMock).existsByName("invalid_name");
    }

    @DisplayName("Successfully create dinner place entity")
    @Test
    void saveDinnerPlace() {

        when(dinnerPlaceRepositoryMock.save(any(DinnerPlaceEntity.class))).thenReturn(dinnerPlaceEntityTest);

        DinnerPlaceEntity savedDinnerPlace = serviceToTest.saveDinnerPlace(dinnerPlaceEntityTest);

        verify(dinnerPlaceRepositoryMock).save(dinnerPlaceEntityTest);

        assertThat(savedDinnerPlace).isNotNull();
    }
}