package com.example.todobulgaria.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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


    @GetMapping("/profile")
    public String getAdminProfile(){

        return "profile";
    }

}
