package com.example.todobulgaria.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.todobulgaria.models.bindings.CreateReviewBidingModel;
import com.example.todobulgaria.models.entities.*;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.repositories.PictureRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.services.CloudinaryImage;
import com.example.todobulgaria.services.CloudinaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WithMockUser("pesho")
@SpringBootTest
@AutoConfigureMockMvc
class ReviewRestControllerTest {

    private static final String REVIEW_1 = "comment1";
    private static final String REVIEW_2 = "comment2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser;
    private PictureEntity testPicture;
    private MultipartFile multipartFile;
    private CloudinaryImage cloudinaryImage;
    private FileInputStream fileInputStream;

    @BeforeEach
    void setUp() throws IOException {
        testUser = new UserEntity();
        testUser.setPassword("password");
        testUser.setUsername("pesho");
        testUser.setEmail("pesho@gmail.com");
        testUser.setFirstName("Petyr");
        testUser.setLastName("Petrov");

        testUser = userRepository.save(testUser);

        fileInputStream = new FileInputStream("src/main/resources/static/images/flag-round-250.png");

        multipartFile = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileInputStream);

        cloudinaryImage = new CloudinaryImage();
        cloudinaryImage.setPublicId("public_id");
        cloudinaryImage.setUrl("url");

        testPicture = new PictureEntity();
        testPicture.setUrl(cloudinaryImage.getUrl());
        testPicture.setTitle(multipartFile.getOriginalFilename());
        testPicture.setPublicId(cloudinaryImage.getPublicId());

        cloudinaryImage = cloudinaryService.upload(multipartFile);
        testPicture = pictureRepository.save(testPicture);

    }

    @AfterEach
    void tearDown() {
        tripRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllReviews() throws Exception {

        var trip = initComments(initTrip());

        mockMvc.perform(get("/api/" + trip.getId() + "/reviews")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].message", is(REVIEW_1))).
                andExpect(jsonPath("$.[1].message", is(REVIEW_2)));
    }

    @Test
    void createReview() throws Exception {

        CreateReviewBidingModel testComment = new CreateReviewBidingModel();
                testComment.setMessage(REVIEW_1);

        var emptyTrip = initTrip();

        mockMvc.perform(
                post("/api/" + emptyTrip.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/" + emptyTrip.getId() + "/reviews/\\d")))
                .andExpect(jsonPath("$.message").value(is(REVIEW_1)));

    }

    private TripEntity initTrip() {
        CategoryEntity categotyTest = new CategoryEntity();
        categotyTest.setName(CategoryEnum.CITY);

        TripEntity testTrip = new TripEntity();
        testTrip.setStartPoint("start");
        testTrip.setDuration(1);
        testTrip.setItineraries(new ArrayList<>());
        testTrip.setUser(testUser);
        testTrip.setRating(1);
        testTrip.setPicture(testPicture);
        testTrip.setCategoryEntity(categotyTest);

        return tripRepository.save(testTrip);
    }

    private TripEntity initComments(TripEntity trip) {

        ReviewEntity comment1 = new ReviewEntity();
        comment1.setCreatedOn(LocalDate.now());
        comment1.setUser(testUser);
        comment1.setComment(REVIEW_1);
        comment1.setApproved(true);
        comment1.setTrip(trip);

        ReviewEntity comment2 = new ReviewEntity();
        comment2.setCreatedOn(LocalDate.now());
        comment2.setUser(testUser);
        comment2.setComment(REVIEW_2);
        comment2.setApproved(true);
        comment2.setTrip(trip);

        trip.setReviews(List.of(comment1, comment2));

        return tripRepository.save(trip);
    }
}