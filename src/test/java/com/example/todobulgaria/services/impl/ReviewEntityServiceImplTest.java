package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.PictureEntity;
import com.example.todobulgaria.models.entities.ReviewEntity;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.service.CreateReviewServiceModel;
import com.example.todobulgaria.models.views.ReviewEntityViewModel;
import com.example.todobulgaria.repositories.ReviewRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewEntityServiceImplTest {

    @Mock
    private  ReviewRepository reviewRepositoryMock;
    @Mock
    private  TripRepository tripRepositoryMock;
    @Mock
    private  UserEntityService userEntityServiceMock;
    @Mock
    private  TripEntityService tripEntityServiceMock;

    @InjectMocks
    private ReviewEntityServiceImpl serviceTotest;

    private ReviewEntity reviewEntityTest;
    private List<ReviewEntity> reviews;
    private ReviewEntityViewModel reviewEntityViewModelTest;
    private TripEntity tripEntityTest;
    private UserEntity userTest;
    private PictureEntity profilePictureTest;
    private CreateReviewServiceModel createReviewServiceModelTest;

    @BeforeEach
    void setUp() {

        serviceTotest = new ReviewEntityServiceImpl(reviewRepositoryMock,
                tripRepositoryMock, userEntityServiceMock, tripEntityServiceMock);

        profilePictureTest = new PictureEntity();
        profilePictureTest.setPublicId("public_id");
        profilePictureTest.setTitle("title");
        profilePictureTest.setId(1L);
        profilePictureTest.setUrl("url");

        userTest = new UserEntity();
        userTest.setId(1L);
        userTest.setProfilePictureUrl(profilePictureTest);

        reviewEntityTest = new ReviewEntity();
        reviewEntityTest.setTrip(tripEntityTest);
        reviewEntityTest.setApproved(true);
        reviewEntityTest.setComment("comment");
        reviewEntityTest.setCreatedOn(LocalDate.now());
        reviewEntityTest.setId(1L);
        reviewEntityTest.setUser(userTest);

        reviews = new ArrayList<>();
        reviews.add(reviewEntityTest);

        tripEntityTest = new TripEntity();
        tripEntityTest.setId(1L);
        tripEntityTest.setReviews(reviews);

        reviewEntityViewModelTest = new ReviewEntityViewModel();
        reviewEntityViewModelTest.setId(1L);
        reviewEntityViewModelTest.setMessage("comment");
        reviewEntityViewModelTest.setCreatedOn(LocalDate.now());
        reviewEntityViewModelTest.setUser(userTest.getFirstName());
        reviewEntityViewModelTest.setProfilePictureUrl("url");
        reviewEntityViewModelTest.setApprove(true);

        createReviewServiceModelTest = new CreateReviewServiceModel();
        createReviewServiceModelTest.setTripId(tripEntityTest.getId());
        createReviewServiceModelTest.setMessage("comment");
        createReviewServiceModelTest.setUser(userTest.getUsername());

    }

    @DisplayName("Get all reviews by trip id")
    @Test
    void getAllReviewsByTripId() {

        when(tripRepositoryMock.findById(tripEntityTest.getId())).thenReturn(Optional.of(tripEntityTest));

        List<ReviewEntityViewModel> findReviews = serviceTotest.getAllReviewsByTripId(tripEntityTest.getId());

        verify(tripRepositoryMock).findById(tripEntityTest.getId());

        assertThat(findReviews).hasSize(1);

    }

    @DisplayName("Get reviews with unexciting trip id")
    @Test
    void getAllReviewsByTripIdDoesntExist() {

        when(tripRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> serviceTotest.
                getAllReviewsByTripId(2L));

        verify(tripRepositoryMock).findById(2L);

    }

    @DisplayName("Successfully create a comment")
    @Test
    void createComment() {

        when(userEntityServiceMock.findUserByUsername(userTest.getUsername())).thenReturn(Optional.of(userTest));
        when(tripEntityServiceMock.findEntityById(tripEntityTest.getId())).thenReturn(tripEntityTest);
        when(reviewRepositoryMock.save(any(ReviewEntity.class))).thenReturn(reviewEntityTest);

        ReviewEntityViewModel saveComment = serviceTotest.createComment(createReviewServiceModelTest);

        verify(userEntityServiceMock).findUserByUsername(userTest.getUsername());

        assertThat(saveComment).isNotNull();

    }

    @DisplayName("Create a comment with unexciting user")
    @Test
    void createCommentFailure() {

        when(userEntityServiceMock.findUserByUsername(userTest.getUsername())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> serviceTotest.createComment(createReviewServiceModelTest));

    }

    @DisplayName("Create a comment with unexciting trip")
    @Test
    void createCommentFailureInvalidTripId() {

        when(userEntityServiceMock.findUserByUsername(userTest.getUsername())).thenReturn(Optional.of(userTest));

        assertThrows(ObjectNotFoundException.class,
                () -> serviceTotest.createComment(createReviewServiceModelTest));

    }
}