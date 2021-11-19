package com.example.todobulgaria.web;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.dto.ItineraryDto;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.views.TripsArticleViewModel;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/trips")
public class TripsRestController {


    private final TripEntityService tripEntityService;
    private final ItineraryEntityService itineraryEntityService;

    public TripsRestController(TripEntityService tripEntityService, ItineraryEntityService itineraryEntityService) {
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
//
//        @GetMapping("/best")
//        public ResponseEntity<List<TripsArticleViewModel>> showBestTrips(){
//        List<TripsArticleViewModel> bestTrips = tripEntityService.findFirstEightBestTripsOrderByRating();
//
//        return ResponseEntity.ok(bestTrips);
//    }
}
