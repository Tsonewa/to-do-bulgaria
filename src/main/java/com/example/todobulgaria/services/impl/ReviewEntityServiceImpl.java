package com.example.todobulgaria.services.impl;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.entities.ReviewEntity;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.views.ReviewEntityViewModel;
import com.example.todobulgaria.repositories.ReviewRepository;
import com.example.todobulgaria.repositories.TripRepository;
import com.example.todobulgaria.services.ReviewEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewEntityServiceImpl implements ReviewEntityService {

    private final ReviewRepository reviewRepository;
    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;

    public ReviewEntityServiceImpl(ReviewRepository reviewRepository, TripRepository tripRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<ReviewEntityViewModel> getAllReviewsByTripId(Long tripId) {

        TripEntity tripEntity = tripRepository.findById(tripId).orElseThrow(() -> new ObjectNotFoundException(tripId));


        return tripEntity.getReviews()
                .stream()
                .map(this::mapReview)
                .collect(Collectors.toList());
    }

    private ReviewEntityViewModel  mapReview(ReviewEntity reviewEntity) {

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
