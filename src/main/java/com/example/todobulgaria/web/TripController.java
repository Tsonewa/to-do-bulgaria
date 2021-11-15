package com.example.todobulgaria.web;

import com.example.interceptors.LastModelAndViewInterceptor;
import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/trips")
public class TripController {

    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;
    private final ItineraryEntityService itineraryEntityService;
    private final UserEntityService userEntityService;

    public TripController(TripEntityService tripEntityService, ModelMapper modelMapper, ItineraryEntityService itineraryEntityService, UserEntityService userEntityService) {
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
        this.itineraryEntityService = itineraryEntityService;
        this.userEntityService = userEntityService;
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

    @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/{id}/delete")
    public String deleteTrip(@PathVariable Long id,
                              Principal principal) {

        tripEntityService.deleteTrip(id);

        return "redirect:/profile/my-trips";
    }

    @GetMapping("/favourite/{id}")
    public String addFavouriteTrip(@PathVariable Long id, HttpServletRequest request){

        Optional<UserEntity> currentUser = getCurrentUser();

        UserEntity userByUsername = userEntityService
                .findUserByUsername(currentUser.get().getUsername()).orElse(null);

        Set<Long> favouriteTrips = userByUsername.getFavouriteTrips();
        favouriteTrips.add(id);

        userByUsername.setFavouriteTrips(favouriteTrips);

        //ModelAndView mv = (ModelAndView)request.getSession().getAttribute(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
        //return mv;

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    private Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> userEntity = userEntityService.findUserByUsername(username);

        return userEntity;
    }

}
