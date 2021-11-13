package com.example.todobulgaria.web;

import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.models.views.UserProfileViewModel;
import com.example.todobulgaria.services.UserEntityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index(){
            return "index"; }

    @GetMapping("/home")
    public String home(){

        return "auth-home";
    }

    @GetMapping("/about-us")
    public String aboutUs(){
        return "about-us";
    }

}
