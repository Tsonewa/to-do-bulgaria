package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.config.BaseConfig;
import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.service.ItineraryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinerariesDetailsViewModel;
import com.example.todobulgaria.repositories.ItineraryRepository;
import com.example.todobulgaria.services.BreakfastPlaceEntityService;
import com.example.todobulgaria.services.CoffeePlaceEntityService;
import com.example.todobulgaria.services.DinnerPlaceEntityService;
import com.example.todobulgaria.services.HotelEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(classes = {BaseConfig.class})
class ItineraryEntityServiceImplTest {

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private ItineraryRepository itineraryRepositoryMock;
    @Mock
    private BreakfastPlaceEntityService breakfastPlaceEntityServiceMock;
    @Mock
    private DinnerPlaceEntityService dinnerPlaceEntityServiceMock;
    @Mock
    private HotelEntityService hotelEntityServiceMock;
    @Mock
    private CoffeePlaceEntityService coffeePlaceEntityServiceMock;

    @InjectMocks
    private ItineraryEntityServiceImpl serviceToTest;

    private ItineraryEntity itineraryEntityTest;
    private TownEntity townEntityTest;
    private HotelEntity hotelEntityTest;
    private DinnerPlaceEntity dinnerPlaceEntityTest;
    private BreakfastPlaceEntity breakfastPlaceEntityTest;
    private CoffeePlaceEntity coffeePlaceEntityTest;
    private TripEntity tripEntityTest;
    private AttractionEntity attractionEntityTest;
    private ItineraryUpdateServiceModel itineraryUpdateServiceModelTest;
    private List<AttractionEntity> attractions;

    @BeforeEach
    void setUp() {
        serviceToTest = new ItineraryEntityServiceImpl(itineraryRepositoryMock, modelMapper,
                breakfastPlaceEntityServiceMock, dinnerPlaceEntityServiceMock, hotelEntityServiceMock, coffeePlaceEntityServiceMock);

        townEntityTest = new TownEntity();
        townEntityTest.setId(1L);
        townEntityTest.setName("townName");

        hotelEntityTest = new HotelEntity();
        hotelEntityTest.setId(1L);
        hotelEntityTest.setName("hotelName");

        breakfastPlaceEntityTest = new BreakfastPlaceEntity();
        breakfastPlaceEntityTest.setId(1L);
        breakfastPlaceEntityTest.setName("breakfastName");

        coffeePlaceEntityTest = new CoffeePlaceEntity();
        coffeePlaceEntityTest.setId(1L);
        coffeePlaceEntityTest.setName("coffeeName");

        dinnerPlaceEntityTest = new DinnerPlaceEntity();
        dinnerPlaceEntityTest.setId(1L);
        dinnerPlaceEntityTest.setName("dinnerName");

        tripEntityTest = new TripEntity();
        tripEntityTest.setId(1L);

        attractionEntityTest = new AttractionEntity();
        attractionEntityTest.setId(1L);
        attractionEntityTest.setName("attractionName");

        attractions = new ArrayList<>();
        attractions.add(attractionEntityTest);

        itineraryEntityTest = new ItineraryEntity();
        itineraryEntityTest.setId(1L);
        itineraryEntityTest.setTown(townEntityTest);
        itineraryEntityTest.setDay(1);
        itineraryEntityTest.setCreatedOn(LocalDate.now());
        itineraryEntityTest.setHotelEntity(hotelEntityTest);
        itineraryEntityTest.setDinnerPlaceEntity(dinnerPlaceEntityTest);
        itineraryEntityTest.setCoffeePlaceEntity(coffeePlaceEntityTest);
        itineraryEntityTest.setBreakfastPlace(breakfastPlaceEntityTest);
        itineraryEntityTest.setTrip(tripEntityTest);
        itineraryEntityTest.setAttractions(attractions);

        itineraryUpdateServiceModelTest = new ItineraryUpdateServiceModel();
        itineraryUpdateServiceModelTest.setBreakfastPlace(breakfastPlaceEntityTest.getName());
        itineraryUpdateServiceModelTest.setCoffeePlace(coffeePlaceEntityTest.getName());
        itineraryUpdateServiceModelTest.setTripId(tripEntityTest.getId());
        itineraryUpdateServiceModelTest.setId(1L);
        itineraryUpdateServiceModelTest.setDinnerPlace(dinnerPlaceEntityTest.getName());
        itineraryUpdateServiceModelTest.setHotel(hotelEntityTest.getName());
        itineraryUpdateServiceModelTest.setBreakfastPlaceAddress("breakfastAddress");

    }

    @DisplayName("Create itinerary successfully")
    @Test
    void saveItinerary() {

        when(itineraryRepositoryMock.saveAndFlush(any(ItineraryEntity.class))).thenReturn(itineraryEntityTest);

        ItineraryEntity savedItineraryEntity = serviceToTest.saveItinerary(itineraryEntityTest);

        verify(itineraryRepositoryMock).saveAndFlush(itineraryEntityTest);

        assertThat(savedItineraryEntity).isNotNull();
    }

    @DisplayName("Find existing itinerary by id")
    @Test
    void findById() {

        when(itineraryRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(itineraryEntityTest));

        ItineraryUpdateServiceModel itineraryFindById = serviceToTest.findById(itineraryEntityTest.getId());

        verify(itineraryRepositoryMock).findById(any(Long.class));

        assertThat(itineraryFindById).isNotNull();
    }

    @DisplayName("Find nonexistent itinerary by id")
    @Test
    void findByIdDoesntExist() {

        when(itineraryRepositoryMock.findById(tripEntityTest.getId())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> serviceToTest.findById(tripEntityTest.getId()));

        verify(itineraryRepositoryMock).findById(tripEntityTest.getId());
    }

    @DisplayName("Successfully update itinerary")
    @Test
    void updateItinerary() {

        when(itineraryRepositoryMock.findById(itineraryUpdateServiceModelTest.getId())).thenReturn(Optional.of(itineraryEntityTest));
        when(itineraryRepositoryMock.save(any(ItineraryEntity.class))).thenReturn(itineraryEntityTest);

        ItineraryEntity itineraryEntityUpdate = serviceToTest.updateItinerary(itineraryUpdateServiceModelTest);

        verify(itineraryRepositoryMock).findById(itineraryUpdateServiceModelTest.getId());

        assertThat(itineraryEntityUpdate).isNotNull();
    }

    @DisplayName("Update nonexistent itinerary")
    @Test
    void updateItineraryFailure() {

        when(itineraryRepositoryMock.findById(itineraryEntityTest.getId())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> serviceToTest.findById(itineraryEntityTest.getId()));

        verify(itineraryRepositoryMock).findById(itineraryEntityTest.getId());
    }
}