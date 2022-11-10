package com.owasptesting.controller;



import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RolesAllowed("admin")
public class AdminController {
  @GetMapping("/home")
  public String getAllUsers() {
      return "welcome admin";
 }
}
