package com.example.todobulgaria.web;

import com.example.todobulgaria.models.dto.ItineraryDto;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripsSearchRestController {


    private final TripEntityService tripEntityService;
    private final ItineraryEntityService itineraryEntityService;

    public TripsSearchRestController(TripEntityService tripEntityService, ItineraryEntityService itineraryEntityService) {
        this.tripEntityService = tripEntityService;
        this.itineraryEntityService = itineraryEntityService;
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<TripsDto>> getBooks(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){

        return ResponseEntity.ok(
                tripEntityService.getTrips(pageNo, pageSize, sortBy));
    }
}