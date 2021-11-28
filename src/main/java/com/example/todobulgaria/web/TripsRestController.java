package com.example.todobulgaria.web;

import com.example.todobulgaria.models.views.TripsArticleViewModel;
import com.example.todobulgaria.services.TripEntityService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")
public class TripsRestController {


    private final TripEntityService tripEntityService;

    public TripsRestController(TripEntityService tripEntityService) {
        this.tripEntityService = tripEntityService;
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<TripsArticleViewModel>> getTrips(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy){

        return ResponseEntity.ok(
                tripEntityService.getTrips(pageNo, pageSize, sortBy));
    }
}
