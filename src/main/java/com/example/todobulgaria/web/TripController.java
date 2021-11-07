package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.AddItineraryBindingModel;
import com.example.todobulgaria.models.service.AddItineraryServiceModel;
import com.example.todobulgaria.services.ItineraryEntityService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public TripController(ItineraryEntityService itineraryEntityService, ModelMapper modelMapper) {
        this.itineraryEntityService = itineraryEntityService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String getAddTripForm(){
        return "add-trip";
    }

    @PostMapping("/add")
    public String addItinerary(@Valid AddItineraryBindingModel addItineraryBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addItineraryBindingModel", addItineraryBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addItineraryBindingModel", bindingResult);

            return "redirect:/add";
        }

        itineraryEntityService
                .createItinerary(modelMapper
                        .map(addItineraryBindingModel,
                                AddItineraryServiceModel.class));

        return "index";
    }
}
