package com.example.todobulgaria.web;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.bindings.SearchBindingModel;
import com.example.todobulgaria.models.dto.WeatherDto;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.NoneExistingTownsWeatherAPIEnum;
import com.example.todobulgaria.models.service.AddTripServiceModel;
import com.example.todobulgaria.models.views.TripDetailsView;
import com.example.todobulgaria.models.views.TripsArticleViewModel;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/trips")
public class TripController {

    private static final String API_KEY = "99e6406a5dbd944e77648f68cd84fb42";
    private static final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";

    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;
    private final UserEntityService userEntityService;
    private final Gson gson;

    public TripController(TripEntityService tripEntityService, ModelMapper modelMapper,  UserEntityService userEntityService,  Gson gson) {
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
        this.userEntityService = userEntityService;
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
                               RedirectAttributes redirectAttributes, Principal principal) throws IOException {

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

        trip.setCreator(principal.getName());

        tripEntityService
                .createTrip(trip);

        return "auth-home";
    }

    @GetMapping("/best")
    public String showBestTrips(Model model){
        model.addAttribute("bestTrips", tripEntityService
                .findFirstEightBestTripsOrderByRating());
        return "best-trips";
    }

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

        String townName = tripById.getStartPoint();

        // Check if the passing town exist it the OpenWeather API -> if not it throws exception handled by Global exception
        townName = checkIfTownExistInWeatherApi
                (townName);

        // Create the url
        String urlString = OPEN_WEATHER_URL + townName + ",BG&units=metric&appid=" + API_KEY;

        //Open a new connection and start a InputStream to fetch all the data from the created URL
         try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

            //Use Gson to map the result into list of json objects
            Map<String, Object> listApi = jsonToMap(result.toString());

            List<Map<String, Object >> mapList = (List<Map<String, Object>>) (listApi.get("list"));

            List<Map<String, Object >> mainList = new ArrayList<>();
            //Get the main list -> json object includes nested objects with information about the 5 days weather forecast starting from the current day
            for (int i = 0; i < mapList.size(); i++) {

                Map<String, Object> stringObjectMap = mapList.get(i);
                Map<String, Object> main = jsonToMap(stringObjectMap.get("main").toString());

                mainList.add(i, main);
            }

            //Get the next 5 days full name in bulgarian, starting from the current date
            // using LocalDate, StringUtils and DaysOfWeek
            ArrayDeque<String> daysOfWeek = new ArrayDeque<>();

            for (int i = 0; i < 5; i++) {
                LocalDate today = LocalDate.now();
                daysOfWeek.add(
                        StringUtils.capitalize(getDayStringNew(today.plusDays(i),
                                Locale.forLanguageTag("BG"))));
            }

            List<WeatherDto> weatherDtos = new ArrayList<>();
            WeatherDto weatherWidget;

            //Do the mapping for getting the average, min and max temperature
             // The widget json object has 40 nested objects - 8 for each day
             // Iter the indexes and get the wanted measurements
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

    //Check if town exist in the API and handle the exception by adding the none existing value to
    //NoneExistingTownsWeatherAPIEnum and set the value of the closest existing town in the following method
    private String checkIfTownExistInWeatherApi(String townNameWithoutWhitespaces) {

        String town = townNameWithoutWhitespaces;

        String townToLowerCase = town.toLowerCase();

        if(townToLowerCase.equals(NoneExistingTownsWeatherAPIEnum.ELENITE.name().toLowerCase())
        || townToLowerCase.equals(NoneExistingTownsWeatherAPIEnum.ЕЛЕНИТЕ.name().toLowerCase())){
            town = "Бургас";
        } else if(townToLowerCase.equals(NoneExistingTownsWeatherAPIEnum.TATUL.name().toLowerCase())
                || townToLowerCase.equals(NoneExistingTownsWeatherAPIEnum.ТАТУЛ.name().toLowerCase()))
            town = "Момчилград";
        return town;
    }

    @PreAuthorize("isOwner(#id)")
    @DeleteMapping("/{id}/delete")
    @Transactional
    public String deleteTrip(@PathVariable Long id, Principal principal) {

        tripEntityService.deleteTrip(id, principal.getName());

        return "redirect:/profile/my-trips";
    }

    //Add trip to users favourite by clicking on the trip articles cards heart icon and redirect either
    //to best trips or all trips. This is set up by getting the referrers from request headers;
    @Transactional
    @GetMapping("/favourite/{id}")
    public String addFavouriteTrip(@PathVariable Long id, HttpServletRequest request, Principal principal){

        UserEntity userByUsername = userEntityService.findUserByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with username " + principal.getName() + " does not exist"));

        Set<TripEntity> favouriteTrips = userByUsername.getFavouriteTrips();
        favouriteTrips.add(tripEntityService.findEntityById(id));

        userEntityService.updateUser(userByUsername);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    //Used to help with the weather api fetched json file
    public Map<String, Object> jsonToMap(String str) {

        return gson.fromJson(str,
                new TypeToken<HashMap<String, Object>>() {
                }
                        .getType());
    }


    public static String getDayStringNew(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }

    //Search for existing trips by tree parameters -> startPoint, duration and category
    @GetMapping("/search")
    public String search(SearchBindingModel searchBindingModel,
                       Model model) {

        List<TripsArticleViewModel> list;
        //Check if we had entered valid search date param
        if(!searchBindingModel.getStartDate().trim().isEmpty()
                && !searchBindingModel.getEndDate().trim().isEmpty()) {
            LocalDate localDateStart = LocalDate.parse(searchBindingModel.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate localDateEnd = LocalDate.parse(searchBindingModel.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            //and if we do then calculate the duration by counting the number of days
            // between the entered dates
            int duration = (int) DAYS.between(localDateStart, localDateEnd);
            //also make inner validation for existing category search param
            //by default it is set to empty string in the search form in the thymeleaf template
            if(!searchBindingModel.getCategoryName().trim().isEmpty()){
                // if we do have valid search params for date and category and optional for start point(which gonna be escaped if passing an empty value)
                // the call will be make - will find all the trip with the entered start point, duration and location
                list = tripEntityService
                        .getAllByStartPointDurationAndCategory(searchBindingModel.getStartPoint(),
                                duration, (long) CategoryEnum.valueOf(searchBindingModel.getCategoryName()).ordinal());
            }else {
            //if we do not set a category param the call will be make for getting result filtered by start point and duration(or again only duration if passing an empty value)
                list = tripEntityService
                        .getAllByStartPointAndDuration(searchBindingModel.getStartPoint(), duration);
            }
            // if we do not hit any of the previous conditions then
            // we simply do the search by entry point param
            // which gonna return no matches if the empty value is being passed
            }else {

            list = tripEntityService
                    .getAllByStartPoint(searchBindingModel.getStartPoint());
        }

        // pass the matching trips list to the view /search
        model.addAttribute("list", list);
        //
        model.addAttribute("searchedTown", searchBindingModel.getStartPoint());

        return "search";
    }
}