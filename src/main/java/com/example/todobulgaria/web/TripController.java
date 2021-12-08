package com.example.todobulgaria.web;

import com.example.todobulgaria.exceptions.ObjectNotFoundException;
import com.example.todobulgaria.models.bindings.AddTripBindingModel;
import com.example.todobulgaria.models.bindings.SearchBindingModel;
import com.example.todobulgaria.models.dto.WeatherDto;
import com.example.todobulgaria.models.entities.TripEntity;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.enums.CategoryEnum;
import com.example.todobulgaria.models.enums.UnexcitingTownsWeatherAPIEnum;
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
        model.addAttribute("bestTrips", tripEntityService.findFirstEightBestTripsOrderByRating());
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
        } else if(townToLowerCase.equals(UnexcitingTownsWeatherAPIEnum.TATUL.name().toLowerCase())
                || townToLowerCase.equals(UnexcitingTownsWeatherAPIEnum.ТАТУЛ.name().toLowerCase()))
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

    @Transactional
    @GetMapping("/favourite/{id}")
    public String addFavouriteTrip(@PathVariable Long id, HttpServletRequest request, Principal principal){

        if(principal.getName().isBlank()){
             throw new ObjectNotFoundException(id);
        }

        Optional<UserEntity> userByUsername = userEntityService.findUserByUsername(principal.getName());

        Set<TripEntity> favouriteTrips = userByUsername.get().getFavouriteTrips();
        favouriteTrips.add(tripEntityService.findEntityById(id));

        userEntityService.updateUser(userByUsername.orElse(null));

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
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
    public String search(SearchBindingModel searchBindingModel,
                       Model model) {

        List<TripsArticleViewModel> list;

        if(!searchBindingModel.getStartDate().trim().isEmpty()
                && !searchBindingModel.getEndDate().trim().isEmpty()) {
            LocalDate localDateStart = LocalDate.parse(searchBindingModel.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate localDateEnd = LocalDate.parse(searchBindingModel.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

            int duration = (int) DAYS.between(localDateStart, localDateEnd);

            if(!searchBindingModel.getCategoryName().trim().isEmpty()){

                list = tripEntityService
                        .getAllByStartPointDurationAndCategory(searchBindingModel.getStartPoint(),
                                duration, (long) CategoryEnum.valueOf(searchBindingModel.getCategoryName()).ordinal());
            }else {

                list = tripEntityService
                        .getAllByStartPointAndDuration(searchBindingModel.getStartPoint(), duration);
            }
            }else {

            list = tripEntityService
                    .getAllByStartPoint(searchBindingModel.getStartPoint());
        }

        model.addAttribute("list", list);
        model.addAttribute("searchedTown", searchBindingModel.getStartPoint());

        return "search";
    }
}
