package com.example.todobulgaria.web;

import com.example.todobulgaria.models.views.ReviewEntityViewModel;
import com.example.todobulgaria.services.ReviewEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ReviewRestController {

    private final ReviewEntityService reviewEntityService;

    public ReviewRestController(ReviewEntityService reviewEntityService) {
        this.reviewEntityService = reviewEntityService;
    }

    @GetMapping("/api/{tripId}/reviews")
    public ResponseEntity<List<ReviewEntityViewModel>> getAllReviews(@PathVariable Long tripId, Principal principal){


        return ResponseEntity.ok(reviewEntityService
                .getAllReviewsByTripId(tripId));

    }
}
