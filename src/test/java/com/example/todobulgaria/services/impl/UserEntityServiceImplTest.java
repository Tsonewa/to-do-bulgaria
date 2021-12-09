package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.config.BaseConfig;
import com.example.todobulgaria.exceptions.UserDuplicationException;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.RoleEnum;
import com.example.todobulgaria.models.service.UserRegisterServiceModel;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.RoleRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.security.UserDetailsImpl;
import com.example.todobulgaria.services.CloudinaryImage;
import com.example.todobulgaria.services.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(classes = {BaseConfig.class})
class UserEntityServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Autowired
    public PasswordEncoder passwordEncoder;

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
    private MultipartFile multipartFile;
    private CloudinaryImage cloudinaryImageTest;
    private FileInputStream fileInputStreamTest;

    @BeforeEach
    void setUp() throws IOException {

        userDetailsMock = new UserDetailsImpl(userRepositoryMock);

        serviceToTest = new UserEntityServiceImpl(userRepositoryMock,
                passwordEncoder, roleRepositoryMock, modelMapperMock,
                userDetailsMock, cloudinaryServiceMock, pictureRepositoryMock);

        testUserRole = new RoleEntity();
        testAdminRole = new RoleEntity();

        testAdminRole.setRole(RoleEnum.ADMIN);
        testUserRole.setRole(RoleEnum.USER);

        fileInputStreamTest = new FileInputStream("src/main/resources/static/images/flag-round-250.png");
        multipartFile = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileInputStreamTest);
        cloudinaryImageTest = new CloudinaryImage();
        cloudinaryImageTest.setPublicId("public_id");
        cloudinaryImageTest.setUrl("url");

        testPicture = new PictureEntity();

        testPicture.setTitle(multipartFile.getOriginalFilename());
        testPicture.setUrl(cloudinaryImageTest.getUrl());
        testPicture.setId(1L);
        testPicture.setPublicId(cloudinaryImageTest.getPublicId());

        pictureRepositoryMock.save(testPicture);

        testUser = new UserEntity();

        testUser.setId(1L);
        testUser.setUsername("pesho");
        testUser.setLastName("Petrov");
        testUser.setFirstName("Pesho");
        testUser.setEmail("petyr@gmail.com");
        testUser.setPassword("12345");
        testUser.setStatus(true);
        testUser.setRoles(List.of(testAdminRole, testUserRole));
        testUser.setProfilePictureUrl(pictureRepositoryMock.getById(1L));
        testUser.setTrips(new ArrayList<>());
        testUser.setFavouriteTrips(new HashSet<>());

        testUserRegisterServiceModel = new UserRegisterServiceModel();

        testUserRegisterServiceModel.setUsername("pesho");
        testUserRegisterServiceModel.setEmail("gosho@gmail.com");
        testUserRegisterServiceModel.setFirstName("Georgi");
        testUserRegisterServiceModel.setLastName("Georgiev");
        testUserRegisterServiceModel.setPassword("12345");
        testUserRegisterServiceModel.setConfirmPassword("12345");
        testUserRegisterServiceModel.setProfilePictureUrl(multipartFile);
    }

    @DisplayName("Successful user registration")
    @Test
    @WithMockUser
    void registrarUserSuccess() throws IOException {

        when(userRepositoryMock.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(roleRepositoryMock.findByRole(RoleEnum.USER)).thenReturn(Optional.of(testUserRole));
        when(modelMapperMock.map(testUserRegisterServiceModel, UserEntity.class)).thenReturn(testUser);
        when(cloudinaryServiceMock.upload(multipartFile)).thenReturn(cloudinaryImageTest);
        when(userRepositoryMock.findUserEntityByUsername(testUser.getUsername())).
                thenReturn(Optional.of(testUser));

        UserEntity newUser = serviceToTest.registrarUser(testUserRegisterServiceModel);

       assertThat(newUser).isNotNull();

       verify(userRepositoryMock, times(1)).save(Mockito.any(UserEntity.class));

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

        verify(userRepositoryMock).findUserEntityByUsername(testUser.getUsername());

        assertThat(foundUser).isNotNull();
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
}