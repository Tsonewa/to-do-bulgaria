package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.DetailsEntityViewModel;
import com.example.todobulgaria.models.views.ItinerariesDetailsViewModel;
import com.example.todobulgaria.models.views.TripCategoryTownDurationViewModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.*;
import com.example.todobulgaria.config.BaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(classes = {BaseConfig.class})
class TripEntityServiceImplTest {

    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private TripRepository tripRepositoryMock;
    @Mock
    private CategoryEntityService categoryEntityServiceMock;
    @Mock
    private TownEntityService townEntityServiceMock;
    @Mock
    private AttractionEntityService attractionEntityServiceMock;
    @Mock
    private DetailsEntityService detailsEntityServiceMock;
    @Mock
    private UserEntityService userEntityServiceMock;
    @Mock
    private CloudinaryService cloudinaryServiceMock;
    @Mock
    private PictureRepository pictureRepositoryMock;
    @Mock
    private BreakfastPlaceEntityService breakfastPlaceEntityServiceMock;
    @Mock
    private CoffeePlaceEntityService coffeePlaceEntityServiceMock;
    @Mock
    private HotelEntityService hotelEntityServiceMock;
    @Mock
    private DinnerPlaceEntityService dinnerPlaceEntityServiceMock;


    @InjectMocks
    private TripEntityServiceImpl serviceToTest;

    private AddTripServiceModel addTripServiceModelTest;
    private MultipartFile multipartFile;
    private CloudinaryImage cloudinaryImageTest;
    private FileInputStream fileInputStreamTest;
    private TownEntity townEntityTest;
    private CoffeePlaceEntity coffeePlaceEntityTest;
    private HotelEntity hotelEntityTest;
    private DinnerPlaceEntity dinnerPlaceEntityTest;
    private AttractionEntity attractionEntityTest;
    private BreakfastPlaceEntity breakfastPlaceEntityTest;
    private List<String> breakfastPlacesTest;
    private List<String> coffeePlacesTest;
    private List<String> hotelsTest;
    private List<String> dinnerPlacesTest;
    private List<String> attractionsNameTest;
    private List<ItineraryEntity> itineraryEntitiesListTest;
    private List<AttractionEntity> attractionEntityList;
    private TripEntity tripEntityTest;
    private CategoryEntity categoryEntityTest;
    private PictureEntity pictureEntityTest;
    private UserEntity userTest;
    private ItineraryEntity itineraryEntityTest;
    private RoleEntity testAdminRole, testUserRole;
    private DetailsEntity detailsEntityTest;
    private TripDetailsView tripDetailsViewTest;
    private ItinerariesDetailsViewModel itinerariesDetailsViewModelTest;
    private List<ItinerariesDetailsViewModel> itineraryDetailsList;
    private DetailsEntityViewModel detailsEntityViewModelTest;
    private TripCategoryTownDurationViewModel userTripsListViewModelTest;
    private List<TripEntity> userTripsTest;

    @BeforeEach
    void setUp() throws IOException {

        serviceToTest = new TripEntityServiceImpl(modelMapper, tripRepositoryMock, categoryEntityServiceMock, townEntityServiceMock, attractionEntityServiceMock, detailsEntityServiceMock, userEntityServiceMock, cloudinaryServiceMock, pictureRepositoryMock, breakfastPlaceEntityServiceMock, coffeePlaceEntityServiceMock,hotelEntityServiceMock, dinnerPlaceEntityServiceMock);

        fileInputStreamTest = new FileInputStream("src/main/resources/static/images/flag-round-250.png");

        multipartFile = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileInputStreamTest);

        cloudinaryImageTest = new CloudinaryImage();
        cloudinaryImageTest.setPublicId("public_id");
        cloudinaryImageTest.setUrl("url");

        pictureEntityTest = new PictureEntity();
        pictureEntityTest.setUrl(cloudinaryImageTest.getUrl());
        pictureEntityTest.setTitle(multipartFile.getOriginalFilename());
        pictureEntityTest.setPublicId(cloudinaryImageTest.getPublicId());
        pictureEntityTest.setId(1L);

        categoryEntityTest = new CategoryEntity();
        categoryEntityTest.setId(1L);
        categoryEntityTest.setName(CategoryEnum.CITY);

        testUserRole = new RoleEntity();
        testAdminRole = new RoleEntity();

        testAdminRole.setRole(RoleEnum.ADMIN);
        testUserRole.setRole(RoleEnum.USER);

        userTest = new UserEntity();

        userTest.setId(1L);
        userTest.setUsername("pesho");
        userTest.setLastName("Petrov");
        userTest.setFirstName("Pesho");
        userTest.setEmail("petyr@gmail.com");
        userTest.setPassword("12345");
        userTest.setStatus(true);
        userTest.setRoles(List.of(testAdminRole, testUserRole));
        userTest.setProfilePictureUrl(pictureRepositoryMock.getById(1L));
        userTest.setTrips(new ArrayList<>());
        userTest.setFavouriteTrips(new HashSet<>());

        townEntityTest = new TownEntity();
        townEntityTest.setId(1L);
        townEntityTest.setName("townName");

        List<String> townsTest = new ArrayList<>();
        townsTest.add(townEntityTest.getName());

        breakfastPlaceEntityTest = new BreakfastPlaceEntity();
        breakfastPlaceEntityTest.setId(1L);
        breakfastPlaceEntityTest.setName("breakfastPlace");

        breakfastPlacesTest = new ArrayList<>();
        breakfastPlacesTest.add(breakfastPlaceEntityTest.getName());

        coffeePlaceEntityTest = new CoffeePlaceEntity();
        coffeePlaceEntityTest.setId(1L);
        coffeePlaceEntityTest.setName("coffeePlaceName");

        coffeePlacesTest = new ArrayList<>();
        coffeePlacesTest.add(coffeePlaceEntityTest.getName());

        dinnerPlaceEntityTest = new DinnerPlaceEntity();
        dinnerPlaceEntityTest.setId(1L);
        dinnerPlaceEntityTest.setName("dinnerPlaceName");

        dinnerPlacesTest = new ArrayList<>();
        dinnerPlacesTest.add(dinnerPlaceEntityTest.getName());

        hotelEntityTest = new HotelEntity();
        hotelEntityTest.setId(1L);
        hotelEntityTest.setName("hotelName");

        hotelsTest = new ArrayList<>();
        hotelsTest.add(hotelEntityTest.getName());

        attractionEntityTest = new AttractionEntity();
        attractionEntityTest.setId(1L);
        attractionEntityTest.setName("attractionName");

        attractionsNameTest = new ArrayList<>();
        attractionsNameTest.add(attractionEntityTest.getName());

        attractionEntityList = new ArrayList<>();
        attractionEntityList.add(attractionEntityTest);

        detailsEntityTest = new DetailsEntity();
        detailsEntityTest.setFotoTips("foto");
        detailsEntityTest.setEquipment("equipment");
        detailsEntityTest.setFestivals("festivals");
        detailsEntityTest.setId(1L);

        detailsEntityViewModelTest = new DetailsEntityViewModel();
        detailsEntityViewModelTest.setEquipment(detailsEntityTest.getEquipment());
        detailsEntityViewModelTest.setFestivals(detailsEntityTest.getFestivals());
        detailsEntityViewModelTest.setFotoTips(detailsEntityTest.getFotoTips());

        addTripServiceModelTest = new AddTripServiceModel();
        addTripServiceModelTest.setStartPoint("Sofia");
        addTripServiceModelTest.setCategoryName(CategoryEnum.CITY);
        addTripServiceModelTest.setUrl(multipartFile);
        addTripServiceModelTest.setTownName(townsTest);
        addTripServiceModelTest.setBreakfastPlace(breakfastPlacesTest);
        addTripServiceModelTest.setCoffeePlace(coffeePlacesTest);
        addTripServiceModelTest.setDinnerPlace(dinnerPlacesTest);
        addTripServiceModelTest.setHotel(hotelsTest);
        addTripServiceModelTest.setAttractionsName(attractionsNameTest);
        addTripServiceModelTest.setCreator(userTest.getUsername());
        addTripServiceModelTest.setFestivals(detailsEntityTest.getFestivals());
        addTripServiceModelTest.setEquipment(detailsEntityTest.getEquipment());
        addTripServiceModelTest.setFotoTip(detailsEntityTest.getFotoTips());

        itineraryEntitiesListTest = new ArrayList<>();
        itineraryEntityTest = new ItineraryEntity();
        itineraryEntityTest.setTown(townEntityTest);
        itineraryEntityTest.setCoffeePlaceEntity(coffeePlaceEntityTest);
        itineraryEntityTest.setDinnerPlaceEntity(dinnerPlaceEntityTest);
        itineraryEntityTest.setAttractions(attractionEntityList);
        itineraryEntityTest.setBreakfastPlace(breakfastPlaceEntityTest);
        itineraryEntityTest.setCreatedOn(LocalDate.now());
        itineraryEntityTest.setHotelEntity(hotelEntityTest);
        itineraryEntityTest.setTrip(tripEntityTest);
        itineraryEntityTest.setId(1L);
        itineraryEntityTest.setDay(1);

        itinerariesDetailsViewModelTest = new ItinerariesDetailsViewModel();
        itinerariesDetailsViewModelTest.setTownName(itineraryEntityTest.getTown().getName());
        itinerariesDetailsViewModelTest.setCoffeePlace(coffeePlaceEntityTest.getName());
        itinerariesDetailsViewModelTest.setDinnerPlace(dinnerPlaceEntityTest.getName());
        itinerariesDetailsViewModelTest.setAttractionName(attractionEntityList.get(0).getName());
        itinerariesDetailsViewModelTest.setBreakfastPlace(breakfastPlaceEntityTest.getName());
        itinerariesDetailsViewModelTest.setHotel(hotelEntityTest.getName());
        itinerariesDetailsViewModelTest.setId(itineraryEntityTest.getId());
        itinerariesDetailsViewModelTest.setDay("1");

        itineraryDetailsList = new ArrayList<>();
        itineraryDetailsList.add(itinerariesDetailsViewModelTest);

        tripEntityTest = new TripEntity();
        tripEntityTest.setId(1L);
        tripEntityTest.setCategoryEntity(categoryEntityTest);
        tripEntityTest.setPicture(pictureEntityTest);
        tripEntityTest.setRating(1);
        tripEntityTest.setDuration(1);
        tripEntityTest.setReviews(new ArrayList<>());
        tripEntityTest.setUser(userTest);
        tripEntityTest.setItineraries(itineraryEntitiesListTest);
        tripEntityTest.setStartPoint("Sofia");
        tripEntityTest.setDetails(detailsEntityTest);

        userTripsTest = new ArrayList<>();
        userTripsTest.add(tripEntityTest);

        tripDetailsViewTest = new TripDetailsView();
        tripDetailsViewTest.setId(tripEntityTest.getId());
        tripDetailsViewTest.setUrl(tripEntityTest.getPicture().getUrl());
        tripDetailsViewTest.setCategoryName(tripEntityTest.getCategoryEntity().getName().name());
        tripDetailsViewTest.setDuration(tripEntityTest.getDuration());
        tripDetailsViewTest.setStartPoint(tripEntityTest.getStartPoint());
        tripDetailsViewTest.setDescription(tripEntityTest.getDescription());
        tripDetailsViewTest.setItinaries(itineraryDetailsList);

        userTripsListViewModelTest = new TripCategoryTownDurationViewModel();
        userTripsListViewModelTest.setId(tripEntityTest.getId());
        userTripsListViewModelTest.setStartPoint(tripEntityTest.getStartPoint());
        userTripsListViewModelTest.setCategoryName(tripEntityTest.getCategoryEntity().getName().name());
        userTripsListViewModelTest.setDuration(tripEntityTest.getDuration());

    }

    @DisplayName("Successfully create trip with details")
    @Test
    void createTrip() throws IOException {

        when(cloudinaryServiceMock.upload(multipartFile)).thenReturn(cloudinaryImageTest);
        when(categoryEntityServiceMock.getCategoryByName(addTripServiceModelTest.getCategoryName())).thenReturn(categoryEntityTest);
        when(tripRepositoryMock.save(any(TripEntity.class))).thenReturn(tripEntityTest);
        when(townEntityServiceMock.saveTown(any(TownEntity.class))).thenReturn(townEntityTest);
        when(breakfastPlaceEntityServiceMock.saveBreakfastPlace(any(BreakfastPlaceEntity.class))).thenReturn(breakfastPlaceEntityTest);
        when(coffeePlaceEntityServiceMock.saveCoffeePlace(any(CoffeePlaceEntity.class))).thenReturn(coffeePlaceEntityTest);
        when(dinnerPlaceEntityServiceMock.saveDinnerPlace(any(DinnerPlaceEntity.class))).thenReturn(dinnerPlaceEntityTest);
        when(hotelEntityServiceMock.saveHotel(any(HotelEntity.class))).thenReturn(hotelEntityTest);
        when(attractionEntityServiceMock.findAttractionByName(attractionEntityTest.getName())).thenReturn(attractionEntityTest);
        when(attractionEntityServiceMock.saveAttraction(any(AttractionEntity.class))).thenReturn(attractionEntityTest);
        when(userEntityServiceMock.findUserByUsername(userTest.getUsername())).thenReturn(Optional.of(userTest));
        when(detailsEntityServiceMock.saveDetailsEntity(any(DetailsEntity.class))).thenReturn(detailsEntityTest);

        TripEntity createdTrip = serviceToTest.createTrip(addTripServiceModelTest);
        tripRepositoryMock.save(createdTrip);

        verify(tripRepositoryMock).save(tripEntityTest);

        assertThat(createdTrip).isNotNull();
    }

    @DisplayName("Successfully create trip without optional entities")
    @Test
    void createTripWithoutDetails() throws IOException {

        when(cloudinaryServiceMock.upload(multipartFile)).thenReturn(cloudinaryImageTest);
        when(categoryEntityServiceMock.getCategoryByName(addTripServiceModelTest.getCategoryName())).thenReturn(categoryEntityTest);
        when(tripRepositoryMock.save(any(TripEntity.class))).thenReturn(tripEntityTest);
        when(townEntityServiceMock.saveTown(any(TownEntity.class))).thenReturn(townEntityTest);
        when(breakfastPlaceEntityServiceMock.saveBreakfastPlace(any(BreakfastPlaceEntity.class))).thenReturn(breakfastPlaceEntityTest);
        when(coffeePlaceEntityServiceMock.saveCoffeePlace(any(CoffeePlaceEntity.class))).thenReturn(coffeePlaceEntityTest);
        when(dinnerPlaceEntityServiceMock.saveDinnerPlace(any(DinnerPlaceEntity.class))).thenReturn(dinnerPlaceEntityTest);
        when(hotelEntityServiceMock.saveHotel(any(HotelEntity.class))).thenReturn(hotelEntityTest);
        when(attractionEntityServiceMock.findAttractionByName(attractionEntityTest.getName())).thenReturn(attractionEntityTest);
        when(attractionEntityServiceMock.saveAttraction(any(AttractionEntity.class))).thenReturn(attractionEntityTest);
        when(userEntityServiceMock.findUserByUsername(userTest.getUsername())).thenReturn(Optional.of(userTest));
        when(detailsEntityServiceMock.saveDetailsEntity(any(DetailsEntity.class))).thenReturn(null);

        TripEntity createdTrip = serviceToTest.createTrip(addTripServiceModelTest);
        tripRepositoryMock.save(createdTrip);

        verify(tripRepositoryMock).save(tripEntityTest);

        assertThat(createdTrip).isNotNull();
    }

    @DisplayName("Successfully create trip with existing entities")
    @Test
    void createTripWithExistingEntities() throws IOException {

        when(cloudinaryServiceMock.upload(multipartFile)).thenReturn(cloudinaryImageTest);
        when(categoryEntityServiceMock.getCategoryByName(addTripServiceModelTest.getCategoryName())).thenReturn(categoryEntityTest);
        when(tripRepositoryMock.save(any(TripEntity.class))).thenReturn(tripEntityTest);
        when(townEntityServiceMock.existTownEntityByName(townEntityTest.getName())).thenReturn(true);
        when(townEntityServiceMock.findTownByName(townEntityTest.getName())).thenReturn(townEntityTest);

        when(breakfastPlaceEntityServiceMock.existBreakfastPlaceByName(breakfastPlaceEntityTest.getName())).thenReturn(true);
        when(breakfastPlaceEntityServiceMock.findBreakfastPlaceEntityByName(breakfastPlaceEntityTest.getName())).thenReturn(breakfastPlaceEntityTest);

        when(coffeePlaceEntityServiceMock.existCoffeePlaceByName(coffeePlaceEntityTest.getName())).thenReturn(true);
        when(coffeePlaceEntityServiceMock.findCoffeePlaceEntityByName(coffeePlaceEntityTest.getName())).thenReturn(coffeePlaceEntityTest);

        when(dinnerPlaceEntityServiceMock.existDinnerPlaceByName(dinnerPlaceEntityTest.getName())).thenReturn(true);
        when(dinnerPlaceEntityServiceMock.findDinnerPlaceEntityByName(dinnerPlaceEntityTest.getName())).thenReturn(dinnerPlaceEntityTest);

        when(hotelEntityServiceMock.existHotelEntityByName(hotelEntityTest.getName())).thenReturn(true);
        when(hotelEntityServiceMock.findHotelEntityByName(hotelEntityTest.getName())).thenReturn(hotelEntityTest);

        when(attractionEntityServiceMock.attractionExistByName(attractionEntityTest.getName())).thenReturn(true);
        when(attractionEntityServiceMock.findAttractionByName(attractionEntityTest.getName())).thenReturn(attractionEntityTest);

        when(attractionEntityServiceMock.findAttractionByName(attractionEntityTest.getName())).thenReturn(attractionEntityTest);
        when(userEntityServiceMock.findUserByUsername(userTest.getUsername())).thenReturn(Optional.of(userTest));

        TripEntity createdTrip = serviceToTest.createTrip(addTripServiceModelTest);
        tripRepositoryMock.save(createdTrip);

        verify(tripRepositoryMock).save(tripEntityTest);

        assertThat(createdTrip).isNotNull();

    }

    @DisplayName("Get first eight trips order by rating")
    @Test
    void findFirstEightBestTripsOrderByRating() {
    }

    @Test
    void getTrips() {

    }
    @DisplayName("Get trip by id - mapped TripsDetailsView")
    @Test
    void findById() {

        when(tripRepositoryMock.findById(tripEntityTest.getId())).thenReturn(Optional.of(tripEntityTest));

        TripDetailsView returnedTripDetailView = serviceToTest.findById(tripEntityTest.getId());

        verify(tripRepositoryMock).findById(tripEntityTest.getId());

        assertThat(returnedTripDetailView).isNotNull();
    }

    @DisplayName("Get trip by nonexisting trip - mapped TripsDetailsView")
    @Test
    void findByIdDoesntExist() {

        when(tripRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> serviceToTest.findById(any(Long.class)));

        verify(tripRepositoryMock).findById(any(Long.class));
    }

    @DisplayName("Find all trips by existing user id - mapped to TripCategoryTownDurationView")
    @Test
    void findAllByUserId() {

        when(tripRepositoryMock.findAllByUserId(userTest.getId())).thenReturn(userTripsTest);

        List<TripCategoryTownDurationViewModel> userTrips = serviceToTest.findAllByUserId(userTest.getId());

        verify(tripRepositoryMock).findAllByUserId(userTest.getId());

        assertThat(userTrips).hasSize(1);
    }

    @DisplayName("Find all trips by nonexisting user id - mapped to TripCategoryTownDurationView")
    @Test
    void findAllByUserIdDoesntExist() {

        when(tripRepositoryMock.findAllByUserId(any(Long.class))).thenReturn(null);

        assertThrows(ObjectNotFoundException.class, () -> serviceToTest.findAllByUserId(any(Long.class)));

        verify(tripRepositoryMock).findAllByUserId(any(Long.class));
    }

    @Test
    void deleteTrip() {
    }

    @Test
    void findAllTripsById() {
    }

    @DisplayName("Find trip by id")
    @Test
    void findEntityById() {
        when(tripRepositoryMock.findById(tripEntityTest.getId())).thenReturn(Optional.of(tripEntityTest));

        TripDetailsView findTrip = serviceToTest.findById(tripEntityTest.getId());

        verify(tripRepositoryMock).findById(tripEntityTest.getId());

        assertThat(findTrip).isNotNull();
    }

    @DisplayName("Nonexisting trip entity by id")
    @Test
    void findEntityByIdDoesntExist() {
        when(tripRepositoryMock.findById(tripEntityTest.getId())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> serviceToTest.findById(tripEntityTest.getId()));

        verify(tripRepositoryMock).findById(tripEntityTest.getId());

    }

    @DisplayName("Return all trips count")
    @Test
    void tripsCount() {
        when(tripRepositoryMock.findAll()).thenReturn(userTripsTest);

        Integer trips = serviceToTest.tripsCount();
        verify(tripRepositoryMock).findAll();

        assertThat(trips).isEqualTo(1);
    }

    @Test
    void isOwner() {
    }

    @Test
    void getByKeywordAndDuration() {
    }

    @Test
    void getByKeyword() {
    }

    @Test
    void getByKeywordDurationAndCategory() {
    }
}