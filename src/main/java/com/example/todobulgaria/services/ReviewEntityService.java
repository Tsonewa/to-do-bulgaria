package com.example.todobulgaria.services;

import com.example.todobulgaria.models.views.ReviewEntityViewModel;

import java.util.List;

public interface ReviewEntityService {

    List<ReviewEntityViewModel> getAllReviewsByTripId(Long tripId);
}
