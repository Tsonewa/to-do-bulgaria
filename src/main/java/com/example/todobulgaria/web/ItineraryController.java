package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.ItineraryUpdateBindingModel;
import com.example.todobulgaria.models.service.ItineraryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinerariesDetailsViewModel;
import com.example.todobulgaria.models.views.ItineraryUpdateViewModel;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("isOwner(#id)")
    @GetMapping("/itinerary/{id}/edit/{tripId}")
    public String getEditItinerary(@PathVariable Long id,
                                   @PathVariable Long tripId,
                                   Model model) {

        ItineraryUpdateServiceModel itineraryModel =
                itineraryEntityService.findById(id);

        ItineraryUpdateViewModel itineraryUpdateViewModel =
                modelMapper.map(itineraryModel, ItineraryUpdateViewModel.class);
        itineraryUpdateViewModel.setTripId(tripId);

        model.addAttribute("itineraryModel", itineraryUpdateViewModel);

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

        ItineraryUpdateServiceModel itineraryUpdateServiceModel = modelMapper.map
                (itineraryUpdateBindingModel,
                ItineraryUpdateServiceModel.class);

        itineraryUpdateServiceModel.setTripId
                (itineraryUpdateBindingModel.getTripId());

        itineraryEntityService.updateItinerary(itineraryUpdateServiceModel);

        //TODO fix: #isOwner interseptor config -> denied access for random pages 403 = access forbidden

        return "redirect:/trips/" + itineraryUpdateBindingModel.getTripId() + "/details";
    }
}
