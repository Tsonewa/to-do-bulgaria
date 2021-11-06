package com.example.todobulgaria.web;

import com.example.todobulgaria.models.dto.UserRegistrationDto;
import com.example.todobulgaria.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityService userEntityService;

    public UserController(ModelMapper modelMapper, PasswordEncoder passwordEncoder,  UserEntityService userEntityService, UserEntityService userEntityService1) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userEntityService = userEntityService1;
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute("username", username);

        return "redirect:login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @ModelAttribute("userRegistrationDto")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }


    @PostMapping("/register")
    public String registerUser(
            @Valid UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            System.out.println(redirectAttributes);
            return "redirect:register";
        }

         userEntityService.registrarUser(userRegistrationDto);

           return "redirect:/";
    }
}