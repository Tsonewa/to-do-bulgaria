package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;
import com.example.todobulgaria.repositories.BreakfastPlaceRepository;
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
class BreakfastPlaceEntityServiceImplTest {

    @Mock
    private BreakfastPlaceRepository breakfastPlaceRepositoryMock;

    @InjectMocks
    private BreakfastPlaceEntityServiceImpl serviceToTest;

    private BreakfastPlaceEntity breakfastPlaceEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new BreakfastPlaceEntityServiceImpl(breakfastPlaceRepositoryMock);

        breakfastPlaceEntityTest = new BreakfastPlaceEntity();
        breakfastPlaceEntityTest.setName("name");
        breakfastPlaceEntityTest.setBookingUrl("url");
        breakfastPlaceEntityTest.setAddress("address");
        breakfastPlaceEntityTest.setId(1L);

    }

    @DisplayName("Find existing breakfast place by name")
    @Test
    void findBreakfastPlaceEntityByName() {

        when(breakfastPlaceRepositoryMock.findByName(breakfastPlaceEntityTest.getName())).thenReturn(Optional.of(breakfastPlaceEntityTest));

        BreakfastPlaceEntity breakfastPlaceEntityByName = serviceToTest.findBreakfastPlaceEntityByName(breakfastPlaceEntityTest.getName());

        verify(breakfastPlaceRepositoryMock).findByName(breakfastPlaceEntityTest.getName());

        assertThat(breakfastPlaceEntityByName).isNotNull();

    }

    @DisplayName("Not existing breakfast place find by name")
    @Test
    void findBreakfastPlaceEntityByNameDoesNotExist() {

        when(breakfastPlaceRepositoryMock.findByName("invalid_name")).thenReturn(Optional.empty());

        BreakfastPlaceEntity breakfastPlaceEntityByName = serviceToTest.findBreakfastPlaceEntityByName("invalid_name");

        verify(breakfastPlaceRepositoryMock).findByName("invalid_name");

        assertThat(breakfastPlaceEntityByName).isNull();

    }

    @DisplayName("Successfully create new breakfast place")
    @Test
    void saveBreakfastPlace() {

        when(breakfastPlaceRepositoryMock.save(any(BreakfastPlaceEntity.class))).thenReturn(breakfastPlaceEntityTest);

        BreakfastPlaceEntity savedBreakfastPlace =
                serviceToTest.saveBreakfastPlace(breakfastPlaceEntityTest);

        verify(breakfastPlaceRepositoryMock).save(breakfastPlaceEntityTest);

        assertThat(savedBreakfastPlace).isNotNull();
    }

    @DisplayName("Exist breakfast place by name")
    @Test
    void existBreakfastPlaceByName() {

        when(breakfastPlaceRepositoryMock.existsByName(breakfastPlaceEntityTest.getName())).thenReturn(true);

        assertThat(serviceToTest.existBreakfastPlaceByName(breakfastPlaceEntityTest.getName())).isTrue();

        verify(breakfastPlaceRepositoryMock).existsByName(breakfastPlaceEntityTest.getName());
    }

    @DisplayName("Breakfast place does not exist by name")
    @Test
    void existBreakfastPlaceByNameDoesntExist() {

        when(breakfastPlaceRepositoryMock.existsByName(breakfastPlaceEntityTest.getName())).thenReturn(false);

        assertThat(serviceToTest.existBreakfastPlaceByName(breakfastPlaceEntityTest.getName())).isFalse();

        verify(breakfastPlaceRepositoryMock).existsByName(breakfastPlaceEntityTest.getName());
    }


}