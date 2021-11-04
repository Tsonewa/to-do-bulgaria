package com.example.todobulgaria.web;

import com.example.todobulgaria.annotations.PasswordMatches;
import com.example.todobulgaria.annotations.ValidEmail;
import com.example.todobulgaria.models.dto.UserRegistrationDto;
import com.example.todobulgaria.models.entities.UserEntity;
import com.example.todobulgaria.services.UserEntityService;
import com.example.todobulgaria.utils.EmailValidator;
import com.example.todobulgaria.utils.PasswordMatchValidator;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;

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
    public String showRegisterForm(WebRequest webRequest, Model model) {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("user", userRegistrationDto);
        return "register";
    }


    @PostMapping("/register")
    public ModelAndView registerUser(
            @ModelAttribute("user") @Valid UserRegistrationDto userRegistrationDto,
            HttpServletRequest request,
            Errors errors) {

        try {
            UserEntity registered = userEntityService.registrateUser(userRegistrationDto);
        } catch (UsernameNotFoundException uaeEx) {
            return new ModelAndView().addObject("message", "An account for that username/email already exists.");
        }

        return new ModelAndView("/login", "user", userRegistrationDto);
    }
}