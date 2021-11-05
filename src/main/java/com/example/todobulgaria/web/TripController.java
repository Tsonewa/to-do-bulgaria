package com.example.todobulgaria.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trips")
public class TripController {


    @GetMapping("/add")
    public String getAddTripForm(){
        return "add-trip";
    }
}
