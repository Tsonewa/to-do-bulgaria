package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.AddItineraryBindingModel;
import com.example.todobulgaria.models.enums.CategoryEnum;
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
@RequestMapping("/itineraries")
public class ItineraryController {

    private final ItineraryEntityService itineraryEntityService;
    private final ModelMapper modelMapper;

    public ItineraryController(ItineraryEntityService itineraryEntityService, ModelMapper modelMapper) {
        this.itineraryEntityService = itineraryEntityService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String getAddTripForm(){
        return "add-itinerary";
    }

    @PostMapping("/add")
    public String addItinerary(@Valid AddItineraryBindingModel addItineraryBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addItineraryBindingModel", addItineraryBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addItineraryBindingModel", bindingResult);

            return "redirect:add";
        }

        AddItineraryServiceModel itinerary = modelMapper.map(addItineraryBindingModel, AddItineraryServiceModel.class);

        itinerary.setCategory(CategoryEnum.valueOf(addItineraryBindingModel.getCategoryName().toUpperCase()));

        itineraryEntityService
                .createItinerary(itinerary);

        return "index";
    }
}
