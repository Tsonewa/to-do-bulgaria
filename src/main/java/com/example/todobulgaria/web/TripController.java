package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.services.TripEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;


@Controller
@RequestMapping("/trips")
public class TripController {

    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;

    public TripController(TripEntityService tripEntityService, ModelMapper modelMapper) {
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String getAddTripForm(){
        return "add-trip";
    }

    @ModelAttribute
    public AddTripBindingModel addTripBindingModel(){
        return new AddTripBindingModel();
    }

    @PostMapping("/add")
    public String addItinerary(@Valid AddTripBindingModel addTripBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws IOException {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addTripBindingModel", addTripBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addTripBindingModel", bindingResult);

            return "redirect:add";
        }

        AddTripServiceModel trip = modelMapper.map(addTripBindingModel, AddTripServiceModel.class);

        trip.setCategoryName(CategoryEnum.valueOf(addTripBindingModel.getCategoryName().toUpperCase()));
        System.out.println(trip);
        System.out.println(addTripBindingModel);
        tripEntityService
                .createTrip(trip);


        return "index";
    }

    @GetMapping("/best")
    public String showBestTrips(Model model){

        model.addAttribute("bestTrips", tripEntityService.findFirstEightBestTripsOrderByRating());

        return "best-trips";
    }
}
