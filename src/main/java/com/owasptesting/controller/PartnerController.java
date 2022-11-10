package com.owasptesting.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partner")
@RolesAllowed("partner")
public class PartnerController {
	@GetMapping("/home")
	public String partnerHome() {
		return "welcome partner home";
	}
}
