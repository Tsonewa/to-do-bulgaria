package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.Rating;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.services.TripEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


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

        AddTripServiceModel trip =
                modelMapper.map(addTripBindingModel, AddTripServiceModel.class);

        CategoryEnum categoryEnum = CategoryEnum.valueOf(addTripBindingModel.getCategoryName());
        trip.setCategoryName(CategoryEnum.valueOf(categoryEnum.toString()));

        tripEntityService
                .createTrip(trip);


        return "auth-home";
    }

    @GetMapping("/best")
    public String showBestTrips(Model model){

        model.addAttribute("bestTrips", tripEntityService.findFirstEightBestTripsOrderByRating());
//        model.addAttribute("rating", new Rating());
        return "best-trips";
    }

//    @PostMapping("/best")
//    public String saveRating(Rating rating, Model model) {
//        model.addAttribute("rating", rating);
//        return "redirect:best";
//    }

    @GetMapping("/all/{pageNum}")
    public String showAlTrips( @PathVariable(name = "pageNum") int pageNum, Model model){

        Page<TripsDto> allTrips = tripEntityService.getTrips(pageNum - 1, 8, "id");
        long totalItems = allTrips.getTotalElements();
        int totalPages = allTrips.getTotalPages();
        List<TripsDto> listTrips = allTrips.getContent();

        model.addAttribute("trips", listTrips);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);

        return "all-trips";
    }

    @RequestMapping("/trips/all")
    public String pagableBestTripsPage(Model model) {
        return showAlTrips(1, model);
    }

    @GetMapping("/{id}/details")
    public String showTrip(
            @PathVariable Long id, Model model) {

        TripDetailsView tripById = tripEntityService.findById(id);

        String townName = tripById.getItinaries().get(0).getTownName();

        model.addAttribute("trip", tripById);
        model.addAttribute("town", townName);

        return "details";
    }
}
