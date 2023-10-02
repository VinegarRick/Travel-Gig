package com.synex.controller;


import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String home(Principal principal, Model model) {
		System.out.println("Welcome Mr."+principal.getName());
		model.addAttribute("username", principal.getName());
		
		return "home";
		
	}
	
	@GetMapping(value="/bookings")
	public String bookings(Principal principal, Model model) {
		model.addAttribute("username", principal.getName());
		
		return "bookings";
	}
	
}
