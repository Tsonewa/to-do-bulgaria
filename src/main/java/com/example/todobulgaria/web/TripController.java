package com.example.todobulgaria.web;

import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.dto.TripsDto;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/trips")
public class TripController {

    private static String API_KEY = "99e6406a5dbd944e77648f68cd84fb42";
    private static String LOCATION = "Sofia";
    private static String urlString = "https://api.openweathermap.org/data/2.5/forecast?q=" + LOCATION + ",BG&appid=" + API_KEY;

    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;
    private final ItineraryEntityService itineraryEntityService;
    private final UserEntityService userEntityService;
    private final UserRepository userRepository;

    public TripController(TripEntityService tripEntityService, ModelMapper modelMapper, ItineraryEntityService itineraryEntityService, UserEntityService userEntityService, UserRepository userRepository) {
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
        this.itineraryEntityService = itineraryEntityService;
        this.userEntityService = userEntityService;
        this.userRepository = userRepository;
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
        LOCATION = townName;

        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

//            Map<String, Object> rest = jsonToMap(result.toString());
//            System.out.println(rest);
//            //TODO find way to get the needed fields
//            System.out.println();

        model.addAttribute("trip", tripById);
        model.addAttribute("town", townName);

        }catch(IOException e){
            System.out.println(e.getMessage());
        }


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

        Optional<UserEntity> userByUsername = userEntityService.findUserByUsername(currentUser.get().getUsername());

        Set<TripEntity> favouriteTrips = userByUsername.get().getFavouriteTrips();
        favouriteTrips.add(tripEntityService.findEntityById(id));

        userEntityService.updateUser(userByUsername.orElse(null));

//        ModelAndView mv = (ModelAndView)request.getSession().getAttribute(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
//        return mv;

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    private Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> userEntity = userEntityService.findUserByUsername(username);

        return userEntity;
    }

    public Map<String, Object> jsonToMap(String str) {

        Map<String, Object> map = new Gson().fromJson(str,
                new TypeToken<HashMap<String, Object>>() {
                }
                        .getType());

        return map;
    }

}
