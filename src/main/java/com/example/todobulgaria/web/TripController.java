package com.example.todobulgaria.web;

import com.example.todobulgaria.models.dto.AddItineraryDto;
import com.example.todobulgaria.services.ItineraryEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/trips")
public class TripController {

    private final ItineraryEntityService itineraryEntityService;

    public TripController(ItineraryEntityService itineraryEntityService) {
        this.itineraryEntityService = itineraryEntityService;
    }


    @GetMapping("/add")
    public String getAddTripForm(){
        return "add-trip";
    }

    @PostMapping("/add")
    public String addItinerary(@Valid AddItineraryDto addItineraryDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addItineraryDto", addItineraryDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addItineraryDto", bindingResult);

            return "redirect:/add";
        }

        itineraryEntityService.createItinerary(addItineraryDto);

        return "index";
    }
}
