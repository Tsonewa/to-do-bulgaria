package com.example.todobulgaria.web;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.views.TripCategoryTownDurationViewModel;
import com.example.todobulgaria.models.views.UserProfileViewModel;
import com.example.todobulgaria.services.TripEntityService;
import com.example.todobulgaria.services.UserEntityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserEntityService userEntityService;
    private final TripEntityService tripEntityService;

    public ProfileController(UserEntityService userEntityService, TripEntityService tripEntityService) {
        this.userEntityService = userEntityService;
        this.tripEntityService = tripEntityService;
    }

    @GetMapping
    public String getUserProfile(Model model){

        Optional<UserEntity> userEntity = getCurrentUser();

        UserProfileViewModel user = new UserProfileViewModel();
        user.setFirstName(userEntity.get().getFirstName());
        user.setLastName(userEntity.get().getLastName());
        user.setProfilePictureUrl(userEntity.get().getProfilePictureUrl().getUrl());

        model.addAttribute("user", user);

        return "profile";
    }

    private Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> userEntity = userEntityService.findUserByUsername(username);

        return userEntity;
    }

    @GetMapping("/my-trips")
    public String myTrips(Model model){

        Optional<UserEntity> userEntity = getCurrentUser();
        getUserProfile(model);

        List<TripCategoryTownDurationViewModel> userTrips = tripEntityService.findAllByUserId(userEntity.get().getId());

        model.addAttribute("userTrips", userTrips);

        return "profile";
    }


}
