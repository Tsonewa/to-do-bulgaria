package com.example.todobulgaria.models.bindings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateReviewBidingModel {

    @NotBlank
    @Size(min = 10)
    private String message;

    public CreateReviewBidingModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
