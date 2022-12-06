package com.example.todoapptestexample.controllers;

import com.example.todoapptestexample.dto.UserDTO;
import com.example.todoapptestexample.models.User;
import com.example.todoapptestexample.services.RegistrationService;
import com.example.todoapptestexample.util.validators.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginController(UserValidator userValidator, RegistrationService registrationService, ModelMapper modelMapper) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index(Model model) {

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            return "redirect:/todo";
        }

        return "index";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/todo";
        }
        return "register";
    }

    @PostMapping("/register")
    public String create(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult) {

        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        registrationService.register(convertToUser(userDTO));

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/todo";
        }

        return "login";
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
