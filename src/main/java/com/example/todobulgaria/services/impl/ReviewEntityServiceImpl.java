package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.ReviewEntity;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.service.CreateReviewServiceModel;
import com.example.todobulgaria.models.views.ReviewEntityViewModel;
import com.example.todobulgaria.repositories.ReviewRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.ReviewEntityService;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewEntityServiceImpl implements ReviewEntityService {

    private final ReviewRepository reviewRepository;
    private final TripRepository tripRepository;
    private final UserEntityService userEntityService;
    private final TripEntityService tripEntityService;

    public ReviewEntityServiceImpl(ReviewRepository reviewRepository, TripRepository tripRepository, UserEntityService userEntityService, TripEntityService tripEntityService) {
        this.reviewRepository = reviewRepository;
        this.tripRepository = tripRepository;
        this.userEntityService = userEntityService;
        this.tripEntityService = tripEntityService;
    }

    @Transactional
    @Override
    public List<ReviewEntityViewModel> getAllReviewsByTripId(Long tripId) {

        TripEntity tripEntity = tripRepository.findById(tripId).orElseThrow(()
                -> new ObjectNotFoundException(tripId));

        return tripEntity.getReviews()
                .stream()
                .map(this::mapReview)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewEntityViewModel createComment(CreateReviewServiceModel serviceModel) {

        Optional<UserEntity> userByUsername = userEntityService.findUserByUsername(serviceModel.getUser());

        if(userByUsername.isEmpty()){
            throw  new UsernameNotFoundException("User with username " + serviceModel.getUser() + " does not exist");
        }

        TripEntity entityById = tripEntityService.findEntityById(serviceModel.getTripId());

        if(entityById == null){
            throw  new ObjectNotFoundException(serviceModel.getTripId());
        }

        ReviewEntity newReview = new ReviewEntity();
        newReview.setComment(serviceModel.getMessage());
        newReview.setCreatedOn(LocalDate.now());
        newReview.setApproved(true);
        newReview.setUser(userByUsername.orElseThrow());
        newReview.setTrip(entityById);

        reviewRepository.save(newReview);

        return mapReview(newReview);

    }

    private ReviewEntityViewModel mapReview(ReviewEntity reviewEntity) {

        ReviewEntityViewModel reviewModel = new ReviewEntityViewModel();
        reviewModel.setApprove(true);
        reviewModel.setCreatedOn(LocalDate.now());
        reviewModel.setUser(String.format("%s %s", reviewEntity.getUser().getFirstName(),
                reviewEntity.getUser().getLastName()));
        reviewModel.setMessage(reviewEntity.getComment());
        reviewModel.setProfilePictureUrl(reviewEntity.getUser().getProfilePictureUrl().getUrl());
        reviewModel.setId(reviewEntity.getId());

        return reviewModel;
    }
}
