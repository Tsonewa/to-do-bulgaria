package com.example.todobulgaria.services;

import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.BestTripsArticleViewModel;

import java.io.IOException;
import java.util.List;

public interface TripEntityService {
    void createTrip(AddTripServiceModel addTripServiceModel) throws IOException;

    List<BestTripsArticleViewModel> findFirstEightBestTripsOrderByRating();
}


