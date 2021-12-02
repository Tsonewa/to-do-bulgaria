package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.AttractionEntity;
import com.example.todobulgaria.models.entities.BreakfastPlaceEntity;
import com.example.todobulgaria.repositories.AttractionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
class AttractionEntityServiceImplTest {

    @Mock
    private AttractionsRepository attractionsRepositoryMock;

    @InjectMocks
    private AttractionEntityServiceImpl serviceToTest;

    private AttractionEntity attractionEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new AttractionEntityServiceImpl(attractionsRepositoryMock);

        attractionEntityTest = new AttractionEntity();
        attractionEntityTest.setName("name");
        attractionEntityTest.setLocation("location");
        attractionEntityTest.setDescription("description");
    }

    @DisplayName("Find existing attraction by name")
    @Test
    void findAttractionByName() {
        when(attractionsRepositoryMock.findByName(attractionEntityTest.getName())).thenReturn(Optional.of(attractionEntityTest));

        AttractionEntity attraction = serviceToTest.findAttractionByName(attractionEntityTest.getName());

        verify(attractionsRepositoryMock).findByName(attractionEntityTest.getName());

        assertThat(attraction).isNotNull();

    }

    @DisplayName("Not existing attraction entity find by name")
    @Test
    void findAttractionEntityByNameDoesNotExist() {

        when(attractionsRepositoryMock.findByName("invalid_name")).thenReturn(Optional.empty());

        AttractionEntity attractionEntity = serviceToTest.findAttractionByName("invalid_name");

        verify(attractionsRepositoryMock).findByName("invalid_name");

        assertThat(attractionEntity).isNull();

    }

    @DisplayName("Exist attraction entity by name")
    @Test
    void attractionExistByName() {
        when(attractionsRepositoryMock.existsByName(attractionEntityTest.getName())).thenReturn(true);

        assertThat(serviceToTest.attractionExistByName(attractionEntityTest.getName())).isTrue();

        verify(attractionsRepositoryMock).existsByName(attractionEntityTest.getName());
    }

    @DisplayName("Attraction does not exist by name")
    @Test
    void existAttractionByNameDoesntExist() {

        when(attractionsRepositoryMock.existsByName(attractionEntityTest.getName())).thenReturn(false);

        assertThat(serviceToTest.attractionExistByName(attractionEntityTest.getName())).isFalse();

        verify(attractionsRepositoryMock).existsByName(attractionEntityTest.getName());
    }

    @DisplayName("Successfully create new attraction")
    @Test
    void saveAttraction() {

        when(attractionsRepositoryMock.save(any(AttractionEntity.class))).thenReturn(attractionEntityTest);

        AttractionEntity savedAttraction =
                serviceToTest.saveAttraction(attractionEntityTest);

        verify(attractionsRepositoryMock).save(attractionEntityTest);

        assertThat(savedAttraction).isNotNull();
    }
}