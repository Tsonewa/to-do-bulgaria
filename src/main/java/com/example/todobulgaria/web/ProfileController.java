package com.example.todobulgaria.web;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.views.TripCategoryTownDurationViewModel;
import com.example.todobulgaria.models.views.UserProfileViewModel;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserEntityService userEntityService;
    private final TripEntityService tripEntityService;
    private final ModelMapper modelMapper;

    public ProfileController(UserEntityService userEntityService, TripEntityService tripEntityService, ModelMapper modelMapper) {
        this.userEntityService = userEntityService;
        this.tripEntityService = tripEntityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getUserProfile(Model model, Principal principal){

        UserEntity userEntity = userEntityService
                .findUserByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with this username"  + principal.getName() + " does not exist"));

        UserProfileViewModel user = new UserProfileViewModel();
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setProfilePictureUrl(userEntity.getProfilePictureUrl().getUrl());

        model.addAttribute("user", user);

        return "profile";
    }

    @Transactional
    @GetMapping("/my-trips")
    public String myTrips(Model model, Principal principal){

        UserEntity userEntity = userEntityService
                .findUserByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with this username"  + principal.getName() + " does not exist"));
        getUserProfile(model, principal);

        List<TripCategoryTownDurationViewModel> userTrips =
                tripEntityService.findAllByUserId(userEntity
                        .getId());

        model.addAttribute("userTrips", userTrips);

       Set<TripCategoryTownDurationViewModel> favouriteTrips = userEntity.getFavouriteTrips()
               .stream().map(t -> {

                   TripCategoryTownDurationViewModel map = modelMapper.map(t, TripCategoryTownDurationViewModel.class);
                map.setStartPoint(t.getStartPoint());

                return map;

               })
               .collect(Collectors.toSet());

        model.addAttribute("userFavouriteTrips", favouriteTrips);

        return "profile";
    }
}
