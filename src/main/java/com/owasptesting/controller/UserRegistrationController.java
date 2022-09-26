package com.owasptesting.controller;

import com.owasptesting.model.User;
import com.owasptesting.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class UserRegistrationController {
    Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
    @Autowired
    private UserRegistrationService userRegistrationService;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @GetMapping("/login")
    public ModelAndView showLogin() {
        return new ModelAndView("login");
    }

    @GetMapping("/registration")
    public ModelAndView showRegisterationPage(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("signup_form");
    }

    @PostMapping("/save")
    public String registerUserAccount(User user) {
        if (userRegistrationService.checkExistingUser(user.getEmail()) != null) {
            return "User exist";
        }
        userRegistrationService.save(user);
        return "user saved";
    }
    @RolesAllowed("admin")
    @GetMapping("Customers")
    public List<User> getAllUsers() {
        return userRegistrationService.findAll();
    }

}
