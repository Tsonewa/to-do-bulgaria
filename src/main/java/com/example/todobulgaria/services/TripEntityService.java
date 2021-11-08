package com.example.todobulgaria.services;

import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.BestTripsArticleViewModel;

import java.util.List;

public interface TripEntityService {
    void createTrip(AddTripServiceModel addTripServiceModel);

    List<BestTripsArticleViewModel> findFirstEightBestTripsOrderByRating();
}


