package com.app.trek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trek.repository.TrekRepository;

@RestController
public class TrekController {

	@Autowired
	public TrekRepository trek_repo;
	
	@GetMapping("/hello")
	public String getMethod() {
		return "hello";
	}
}

