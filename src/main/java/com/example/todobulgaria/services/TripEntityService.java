package com.example.todobulgaria.services;

import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.models.views.TripsArticleViewModel;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface TripEntityService {
    void createTrip(AddTripServiceModel addTripServiceModel) throws IOException;

    List<TripsArticleViewModel> findFirstEightBestTripsOrderByRating();

    Page<TripsDto> getTrips(
            int pageNo,
            int pageSize,
            String sortBy);

    TripDetailsView findById(Long id);
}



