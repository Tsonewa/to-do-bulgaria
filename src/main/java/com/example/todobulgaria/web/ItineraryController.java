package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.ItineraryUpdateBindingModel;
import com.example.todobulgaria.models.service.ItineryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinariesDetailsViewModel;
import com.example.todobulgaria.security.UserService;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ItineraryController {

    private ItineraryEntityService itineraryEntityService;
    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;

    public ItineraryController(ItineraryEntityService itineraryEntityService, TripEntityService tripEntityService, ModelMapper modelMapper) {
        this.itineraryEntityService = itineraryEntityService;
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isOwner(#id)")
    @GetMapping("/itinerary/{id}/edit")
    public String getEditItinerary(@PathVariable Long id, Model model) {


        ItinariesDetailsViewModel itinariesDetailsViewModel = itineraryEntityService.findById(id);

         ItineraryUpdateBindingModel itineraryModel = modelMapper.map(
                itinariesDetailsViewModel,
                ItineraryUpdateBindingModel.class
        );

        model.addAttribute("itineraryModel", itineraryModel);

        return "update";
    }

    @PatchMapping("/itinerary/{id}/edit")
    public String updateItinerary(
            @PathVariable Long id,
            @Valid ItineraryUpdateBindingModel itineraryUpdateBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("updateModel", itineraryUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateItineraryDto", bindingResult);

            return "redirect:/itinerary/" + id + "/edit";
        }

        ItineryUpdateServiceModel itineraryUpdateServiceModel = modelMapper.map(itineraryUpdateBindingModel,
                ItineryUpdateServiceModel.class);

        itineraryEntityService.updateItinerary(itineraryUpdateServiceModel);

        //TODO fix: redirect to itinerary id instead of trip id
        //TODO fix: #isOwner interseptor config -> denied access for random pages 403 = access forbidden

        return "redirect:/trips/" + id + "/details";
    }
}
