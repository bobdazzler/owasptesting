package com.owasptesting.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RolesAllowed("customers")
public class CustomerController {
	@GetMapping("/home")
	public String userHome() {
		return "welcome customer home";
	}
}
