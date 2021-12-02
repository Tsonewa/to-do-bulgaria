package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.TownEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.repositories.TownRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TownEntityServiceImplTest {

    @Mock
    private TownRepository townRepositoryMock;

    @InjectMocks
    private TownEntityServiceImpl serviceToTest;

    private TownEntity townEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new TownEntityServiceImpl
                (townRepositoryMock);

        townEntityTest = new TownEntity();

        townEntityTest.setName("Name");
        townEntityTest.setId(1L);

    }

    @DisplayName("Find existing town by town name")
    @Test
    void findTownByName() {

        when(townRepositoryMock.getTownEntityByName(townEntityTest.getName())).thenReturn(Optional.of(townEntityTest));

        TownEntity townByName = serviceToTest.findTownByName(townEntityTest.getName());

        assertThat(townByName).isNotNull();

        verify(townRepositoryMock).getTownEntityByName(townEntityTest.getName());

    }

    @DisplayName("Town by town name not found")
    @Test
    void findTownByNameNotFound() {

        when(townRepositoryMock.getTownEntityByName("invalid_town_name"))
                .thenReturn(Optional.empty());

       TownEntity invalid_town_name = serviceToTest.findTownByName("invalid_town_name");

        assertThat(invalid_town_name).isNull();

        verify(townRepositoryMock).getTownEntityByName("invalid_town_name");

    }

    @DisplayName("Town exist by town name")
    @Test
    void existTownEntityByName() {

        when(townRepositoryMock.existsByName(townEntityTest.getName())).thenReturn(true);

        assertThat(serviceToTest.existTownEntityByName(townEntityTest.getName())).isTrue();

        verify(townRepositoryMock).existsByName(townEntityTest.getName());
    }

    @DisplayName("Town does not exist by town name")
    @Test
    void townDoesNotExistByName() {

        when(townRepositoryMock.existsByName("invalid_name")).thenReturn(false);

        assertThat(serviceToTest.existTownEntityByName("invalid_name")).isFalse();

        verify(townRepositoryMock).existsByName("invalid_name");
    }

    @DisplayName("Save town successfully")
    @Test
    void saveTown() {

        when(townRepositoryMock.save(any(TownEntity.class))).thenReturn(townEntityTest);

        TownEntity savedTown = serviceToTest.saveTown(townEntityTest);

        verify(townRepositoryMock).save(any(TownEntity.class));

        assertThat(savedTown).isNotNull();
    }

}