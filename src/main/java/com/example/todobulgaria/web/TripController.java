package com.example.todobulgaria.web;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.dto.WeatherDto;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.UnexcitingTownsWeatherAPIEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.models.views.TripsArticleViewModel;
import com.example.todobulgaria.repositories.UserRepository;
import com.example.todobulgaria.services.ItineraryEntityService;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
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
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequestMapping("/trips")
public class TripController {

    private static String API_KEY = "99e6406a5dbd944e77648f68cd84fb42";
    private static String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";

    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;
    private final ItineraryEntityService itineraryEntityService;
    private final UserEntityService userEntityService;
    private final UserRepository userRepository;
    private final Gson gson;

    public TripController(TripEntityService tripEntityService, ModelMapper modelMapper, ItineraryEntityService itineraryEntityService, UserEntityService userEntityService, UserRepository userRepository, Gson gson) {
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
        this.itineraryEntityService = itineraryEntityService;
        this.userEntityService = userEntityService;
        this.userRepository = userRepository;
        this.gson = gson;
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

        CategoryEnum categoryEnum = CategoryEnum.valueOf(addTripBindingModel
                .getCategoryName().replace("-", "_"));

        trip.setCategoryName(CategoryEnum.valueOf(categoryEnum.name()));

        tripEntityService
                .createTrip(trip);


        return "auth-home";
    }

    @Transactional
    @GetMapping("/best")
    public String showBestTrips(Model model){

        model.addAttribute("bestTrips", tripEntityService.findFirstEightBestTripsOrderByRating());
//        model.addAttribute("rating", new Rating());
        return "best-trips";
    }

    @Transactional
    @GetMapping("/all/{pageNum}")
    public String showAlTrips( @PathVariable(name = "pageNum") int pageNum, Model model){

        Page<TripsArticleViewModel> allTrips = tripEntityService.getTrips(pageNum - 1, 8, "id");
        long totalItems = allTrips.getTotalElements();
        int totalPages = allTrips.getTotalPages();
        List<TripsArticleViewModel> listTrips = allTrips.getContent();

        model.addAttribute("trips", listTrips);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);

        return "all-trips";
    }

    @Transactional
    @RequestMapping("/trips/all")
    public String pagableBestTripsPage(Model model) {
        return showAlTrips(1, model);
    }

    @Transactional
    @GetMapping("/{id}/details")
    public String showTrip(
            @PathVariable Long id, Model model) {

        TripDetailsView tripById = tripEntityService.findById(id);

        if(tripById == null){
            throw new ObjectNotFoundException(id);
        }

        String townName = tripById.getItinaries().get(0).getTownName();

        townName = checkIfTownExistInWeatherApi
                (townName);

        String urlString = OPEN_WEATHER_URL + townName + ",BG&units=metric&appid=" + API_KEY;

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

            Map<String, Object> listApi = jsonToMap(result.toString());

            List<Map<String, Object >> mapList = (List<Map<String, Object>>) (listApi.get("list"));

            List<Map<String, Object >> mainList = new ArrayList<>();

            for (int i = 0; i < mapList.size(); i++) {

                Map<String, Object> stringObjectMap = mapList.get(i);
                Map<String, Object> main = jsonToMap(stringObjectMap.get("main").toString());

                mainList.add(i, main);
            }


            ArrayDeque<String> daysOfWeek = new ArrayDeque<>();

            for (int i = 0; i < 5; i++) {
                LocalDate today = LocalDate.now();
                daysOfWeek.add(
                        StringUtils.capitalize(getDayStringNew(today.plusDays(i),
                                Locale.forLanguageTag("BG"))));
            }

            List<WeatherDto> weatherDtos = new ArrayList<>();
            WeatherDto weatherWidget;

            for (int i = 0; i < mapList.size() ; i+=8) {

                weatherWidget = new WeatherDto();
                weatherWidget.setTemperature(Double.parseDouble(mainList.get(i + 4).get("temp").toString()));
                weatherWidget.setMaxTemperature(Double.parseDouble(mainList.get(i).get("temp_max").toString()));
                weatherWidget.setMinTemperature(Double.parseDouble(mainList.get(i + 7).get("temp_min").toString()));
                weatherWidget.setWeekDay(daysOfWeek.poll());

                weatherDtos.add(weatherWidget);
            }

        model.addAttribute("trip", tripById);
        model.addAttribute("town", townName);
        model.addAttribute("weather", weatherDtos);

        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        return "details";
    }

    private String checkIfTownExistInWeatherApi(String townNameWithoutWhitespaces) {

        String town = townNameWithoutWhitespaces;

        String townToLowerCase = town.toLowerCase();

        if(townToLowerCase.equals(UnexcitingTownsWeatherAPIEnum.ELENITE.name().toLowerCase())
        || townToLowerCase.equals(UnexcitingTownsWeatherAPIEnum.ЕЛЕНИТЕ.name().toLowerCase())){
            town = "Бургас";
        }

        return town;
    }


    @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/{id}/delete")
    public String deleteTrip(@PathVariable Long id) {

        tripEntityService.deleteTrip(id);

        return "redirect:/profile/my-trips";
    }

    @Transactional
    @GetMapping("/favourite/{id}")
    public String addFavouriteTrip(@PathVariable Long id, HttpServletRequest request){

        Optional<UserEntity> currentUser = getCurrentUser();

        if(!userEntityService.findUserByUsername(currentUser.get().getUsername()).isPresent()){
             throw new ObjectNotFoundException(id);
        }
        Optional<UserEntity> userByUsername = userEntityService.findUserByUsername(currentUser.get().getUsername());

        Set<TripEntity> favouriteTrips = userByUsername.get().getFavouriteTrips();
        favouriteTrips.add(tripEntityService.findEntityById(id));

        userEntityService.updateUser(userByUsername.orElse(null));

//        ModelAndView mv = (ModelAndView)request.getSession().getAttributuser_favouritee(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
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

        Map<String, Object> map = gson.fromJson(str,
                new TypeToken<HashMap<String, Object>>() {
                }
                        .getType());

        return map;
    }


    public static String getDayStringNew(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }

    @GetMapping("/search")
    public String home(Model model, String keyword) {
        if(keyword!=null) {
            List<TripEntity> list = tripEntityService.getByKeyword(keyword);
            model.addAttribute("list", list);
        }else {
            Page<TripsArticleViewModel> list =
                    tripEntityService.getTrips(1, 8, "id");

            model.addAttribute("list", list);}

        return "index";
    }

}
