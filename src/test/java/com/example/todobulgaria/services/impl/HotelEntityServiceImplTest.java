package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.models.entities.HotelEntity;
import com.example.todobulgaria.repositories.HotelRepository;
import com.example.todobulgaria.services.HotelEntityService;
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
class HotelEntityServiceImplTest {

    @Mock
    private HotelRepository hotelRepositoryMock;

    @InjectMocks
    private HotelEntityServiceImpl serviceToTest;

    private HotelEntity hotelEntityTest;

    @BeforeEach
    void setUp() {

        serviceToTest = new HotelEntityServiceImpl(hotelRepositoryMock);

        hotelEntityTest = new HotelEntity();
        hotelEntityTest.setId(1L);
        hotelEntityTest.setAddress("address");
        hotelEntityTest.setName("name");
        hotelEntityTest.setBookingUrl("url");
    }

    @DisplayName("Find existing hotel by name")
    @Test
    void findHotelEntityByName() {

        when(hotelRepositoryMock.findByName(hotelEntityTest.getName())).thenReturn(Optional.of(hotelEntityTest));

        HotelEntity hotelEntityByName = serviceToTest.findHotelEntityByName(hotelEntityTest.getName());

        verify(hotelRepositoryMock).findByName(hotelEntityTest.getName());

        assertThat(hotelEntityByName).isNotNull();
    }

    @DisplayName("Not existing hotel search by name")
    @Test
    void findHotelEntityByNameDoesntExist() {

        when(hotelRepositoryMock.findByName("invalid_name")).thenReturn(Optional.empty());

        HotelEntity hotelEntityByName = serviceToTest.findHotelEntityByName("invalid_name");

        verify(hotelRepositoryMock).findByName("invalid_name");

        assertThat(hotelEntityByName).isNull();

    }

    @DisplayName("Exist hotel by name")
    @Test
    void existHotelEntityByName() {

        when(hotelRepositoryMock.existsByName(hotelEntityTest.getName())).thenReturn(true);

        assertThat(serviceToTest.existHotelEntityByName(hotelEntityTest.getName())).isTrue();

        verify(hotelRepositoryMock).existsByName(hotelEntityTest.getName());
    }

    @DisplayName("Hotel does not exist by name")
    @Test
    void existHotelEntityByNameDoesNotExist() {

        when(hotelRepositoryMock.existsByName("invalid_name")).thenReturn(false);

        assertThat(serviceToTest.existHotelEntityByName("invalid_name")).isFalse();

        verify(hotelRepositoryMock).existsByName("invalid_name");
    }

    @DisplayName("Successfully create hotel entity")
    @Test
    void saveHotel() {

        when(hotelRepositoryMock.save(any(HotelEntity.class))).thenReturn(hotelEntityTest);

        HotelEntity savedHotel = serviceToTest.saveHotel(hotelEntityTest);

        verify(hotelRepositoryMock).save(hotelEntityTest);

        assertThat(savedHotel).isNotNull();
    }
}