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
@RequestMapping("/admin")
public class AdminController {

//    private final TripEntityService tripEntityService;
//    private final UserEntityService userEntityService;
//
//    public AdminController(TripEntityService tripEntityService, UserEntityService userEntityService) {
//        this.tripEntityService = tripEntityService;
//        this.userEntityService = userEntityService;
//    }
//
//
//    @GetMapping
//    public String getAdminPanel(Model model){
//
//        Optional<UserEntity> userEntity = getCurrentUser();
//
//        List<TripCategoryTownDurationViewModel> newTrips = tripEntityService.findAllByUserId(userEntity.get().getId());
//
//        model.addAttribute("userTrips", newTrips);
//
//        return "admin";
//    }
//
//    private Optional<UserEntity> getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Optional<UserEntity> userEntity = userEntityService.findUserByUsername(username);
//
//        return userEntity;
//    }


}
