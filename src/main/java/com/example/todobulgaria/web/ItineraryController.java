package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.ItineraryUpdateBindingModel;
import com.example.todobulgaria.models.service.ItineraryUpdateServiceModel;
import com.example.todobulgaria.models.views.ItinerariesDetailsViewModel;
import com.example.todobulgaria.models.views.ItineraryUpdateViewModel;
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
import java.security.Principal;

@Controller
public class ItineraryController {

    private final ItineraryEntityService itineraryEntityService;
    private final ModelMapper modelMapper;

    public ItineraryController(ItineraryEntityService itineraryEntityService, ModelMapper modelMapper) {
        this.itineraryEntityService = itineraryEntityService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isOwner(#tripId)")
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
            @Valid ItineraryUpdateBindingModel itineraryModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("itineraryModel", itineraryModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itineraryModel", bindingResult);

            return "redirect:/itinerary/" + id + "/edit/errors";
        }

        ItineraryUpdateServiceModel itineraryUpdateServiceModel = modelMapper.map
                (itineraryModel,
                ItineraryUpdateServiceModel.class);

        itineraryUpdateServiceModel.setTripId
                (itineraryModel.getTripId());

        itineraryEntityService.updateItinerary(itineraryUpdateServiceModel);

        return "redirect:/trips/" + itineraryModel.getTripId() + "/details";
    }

    @GetMapping("/itinerary/{id}/edit/errors")
    public String editItineraryErrors(@PathVariable Long id, Model model) {

        return "update";
    }
}
