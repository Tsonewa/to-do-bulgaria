package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.CreateReviewBidingModel;
import com.example.todobulgaria.models.service.CreateReviewServiceModel;
import com.example.todobulgaria.models.service.validation.ApiError;
import com.example.todobulgaria.models.views.ReviewEntityViewModel;
import com.example.todobulgaria.services.ReviewEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class ReviewRestController {

    private final ReviewEntityService reviewEntityService;
    private final ModelMapper modelMapper;

    public ReviewRestController(ReviewEntityService reviewEntityService, ModelMapper modelMapper) {
        this.reviewEntityService = reviewEntityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/{tripId}/reviews")
    public ResponseEntity<List<ReviewEntityViewModel>> getAllReviews(@PathVariable Long tripId, Principal principal){


        return ResponseEntity.ok(reviewEntityService
                .getAllReviewsByTripId(tripId));

    }

    @PostMapping("/api/{tripId}/reviews")
    public ResponseEntity<ReviewEntityViewModel> createReview(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long tripId,
            @RequestBody @Valid CreateReviewBidingModel createReviewBindingModel
    ) {

        CreateReviewServiceModel serviceModel =
                modelMapper.map(createReviewBindingModel, CreateReviewServiceModel.class);
        serviceModel.setTripId(tripId);
        serviceModel.setUser(principal.getUsername());

        ReviewEntityViewModel newComment =
                reviewEntityService.createComment(serviceModel);

        URI locationOfNewComment =
                URI.create(String.format("/api/%s/reviews/%s", tripId, newComment.getId()));

        return ResponseEntity.
                created(locationOfNewComment).
                body(newComment);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exc.getFieldErrors().forEach(fe ->
                apiError.addFieldWithError(fe.getField()));

        return ResponseEntity.badRequest().body(apiError);
    }

}
