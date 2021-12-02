package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.UserDuplicationException;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.security.UserDetailsImpl;
import com.example.todobulgaria.services.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.web.servlet.function.ServerResponse.status;

@ExtendWith(MockitoExtension.class)
class UserEntityServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private UserDetailsImpl userDetailsMock;

    @Mock
    private CloudinaryService cloudinaryServiceMock;

    @Mock
    PictureRepository pictureRepositoryMock;

    @InjectMocks
    private UserEntityServiceImpl serviceToTest;

    private UserEntity testUser;
    private PictureEntity testPicture;
    private RoleEntity testAdminRole, testUserRole;
    private UserRegisterServiceModel testUserRegisterServiceModel;

    @BeforeEach
    void setUp(){

        serviceToTest = new UserEntityServiceImpl(userRepositoryMock,
                passwordEncoderMock, roleRepositoryMock, modelMapperMock,
                userDetailsMock, cloudinaryServiceMock, pictureRepositoryMock);

        testUserRole = new RoleEntity();
        testAdminRole = new RoleEntity();

        testAdminRole.setRole(RoleEnum.ADMIN);
        testUserRole.setRole(RoleEnum.USER);

        testPicture = new PictureEntity();
        testPicture.setTitle("Picture");
        testPicture.setUrl("url.png");
        testPicture.setId(1L);
        testPicture.setPublicId("public_id");

        pictureRepositoryMock.save(testPicture);

        testUser = new UserEntity();

        testUser.setUsername("pesho");
        testUser.setLastName("Petrov");
        testUser.setFirstName("Pesho");
        testUser.setEmail("petyr@gmail.com");
        testUser.setPassword(passwordEncoderMock.encode("12345"));
        testUser.setStatus(true);
        testUser.setRoles(List.of(testAdminRole, testUserRole));
        testUser.setProfilePictureUrl(pictureRepositoryMock.getById(1L));
        testUser.setTrips(new ArrayList<>());
        testUser.setFavouriteTrips(new HashSet<>());

        testUserRegisterServiceModel = new UserRegisterServiceModel();

        testUserRegisterServiceModel.setUsername("pesho");
        testUserRegisterServiceModel.setEmail("gosho@gmail.com");
        testUserRegisterServiceModel.setPassword("12345");
        testUserRegisterServiceModel.setFirstName("Georgi");
        testUserRegisterServiceModel.setLastName("Georgiev");
        testUserRegisterServiceModel.setConfirmPassword("12345");

    }

    @DisplayName("Successful user registration")
    @Test
    void registrarUserSuccess() throws IOException {

//        UserEntity newUserTest = new UserEntity();
//
//        newUserTest.setUsername("pesho");
//        newUserTest.setLastName("Georgiev");
//        newUserTest.setFirstName("Gosho");
//        newUserTest.setEmail("gosho@gmail.com");
//        newUserTest.setPassword(passwordEncoderMock.encode("12345"));
//        newUserTest.setStatus(true);
//        newUserTest.setRoles(List.of(testAdminRole, testUserRole));
//        newUserTest.setProfilePictureUrl(testPicture);
//        newUserTest.setTrips(new ArrayList<>());
//        newUserTest.setFavouriteTrips(new HashSet<>());
//
//        when(userRepositoryMock.existsByUsername(testUser.getUsername())).thenReturn(false);
//        when(roleRepositoryMock.findByRole(RoleEnum.USER)).thenReturn(Optional.of(testUserRole));
//        when(modelMapperMock.map(testUserRegisterServiceModel, UserEntity.class)).thenReturn(newUserTest);
//        when(serviceToTest.registrarUser(testUserRegisterServiceModel)).thenReturn(newUserTest);
//
//        UserEntity newUser = serviceToTest.registrarUser(testUserRegisterServiceModel);
//
//        assertThat(newUser).isNotNull();
//
//        verify(userRepositoryMock, times(1)).save(Mockito.any(UserEntity.class));

        //TODO
    }

    @DisplayName("Unsuccessful user registration")
    @Test
    void registrarUserUnsuccessful() {

        when(userRepositoryMock.existsByUsername(testUser.getUsername())).thenReturn(true);

        assertThrows(UserDuplicationException.class, () -> serviceToTest
                .registrarUser(testUserRegisterServiceModel));

        verify(userRepositoryMock).existsByUsername(testUser.getUsername());

    }


    @DisplayName("User with exact username exist")
    @Test
    void existByUsername() {

        when(userRepositoryMock.findUserEntityByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        Optional<UserEntity> foundUser = serviceToTest.findUserByUsername(testUser.getUsername());

        assertThat(foundUser).isNotNull();

        verify(userRepositoryMock).findUserEntityByUsername(testUser.getUsername());

    }

    @DisplayName("Update user successfully")
    @Test
    void updateUserSuccess() {
        CategoryEntity categoryTest = new CategoryEntity();
        categoryTest.setName(CategoryEnum.CITY);

        TripEntity tripEntityTest = new TripEntity();
        tripEntityTest.setId(1L);
        tripEntityTest.setRating(2);
        tripEntityTest.setItineraries(new ArrayList<>());
        tripEntityTest.setPicture(testPicture);
        tripEntityTest.setCategoryEntity(categoryTest);
        tripEntityTest.setStartPoint("StartPoint");
        tripEntityTest.setUser(testUser);

        testUser.getFavouriteTrips().add(tripEntityTest);

        serviceToTest.updateUser(testUser);

        assertThat(testUser.getFavouriteTrips().size()).isEqualTo(1);

    }

    @DisplayName("Find user by passing username param")
    @Test
    void findUserByUsername() {

        when(userRepositoryMock.findUserEntityByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        Optional<UserEntity> foundUser = serviceToTest.findUserByUsername(testUser.getUsername());

        assertThat(foundUser).isNotNull();

        verify(userRepositoryMock).findUserEntityByUsername(testUser.getUsername());
    }

    @DisplayName("User not found by passing username param")
    @Test
    void userByUsernameNotFound() {

        when(userRepositoryMock.findUserEntityByUsername("invalid_username")).thenReturn(null);

        Optional<UserEntity> foundUser = serviceToTest.findUserByUsername("invalid_username");

        assertThat(foundUser).isNull();

        verify(userRepositoryMock).findUserEntityByUsername("invalid_username");
    }

    @DisplayName("User not found by passing username param")
    @Test
    void findUserByUsernameNotFound() {

        when(userRepositoryMock.findUserEntityByUsername("invalid_username")).thenReturn(null);

        Optional<UserEntity> foundUser = serviceToTest.findUserByUsername("invalid_username");

        assertThat(foundUser).isNull();

        verify(userRepositoryMock).findUserEntityByUsername("invalid_username");

    }

    @DisplayName("Upload cloudinary picture")
    @Test
    void createPictureEntity() {

        //TODO

    }
}