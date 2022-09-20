package com.owasptesting.controller;

import com.owasptesting.model.User;
import com.owasptesting.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
public class UserRegistrationController {
    Logger logger =  LoggerFactory.getLogger(UserRegistrationController.class);
    private UserRepository userRepository;
    @GetMapping("/")
    public  ModelAndView index() {
        return new ModelAndView("login");
    }
    @GetMapping("/login")
    public ModelAndView showLogin(){
        return new ModelAndView("login");
    }
    @GetMapping("/registration")
    public String showRegisterationPage() {
        return "RegistrationPage";
    }
    @PostMapping("/save")
    public String registerUserAccount( User user) {
        if( userRepository.findByEmail(user.getEmail()) != null){
            return "User exist";
        }
        userRepository.save( user);
        return "user saved";
    }
    @GetMapping("Customers")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
