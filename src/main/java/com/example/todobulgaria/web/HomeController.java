package com.example.todobulgaria.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
    return "index";
}

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
