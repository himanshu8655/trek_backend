package com.app.trek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.trek.models.MessageModel;
import com.app.trek.models.TrekDetailsPojo;
import com.app.trek.repository.TrekRepository;
import com.app.trek.service.TrekService;

@RestController
public class TrekController {

	@Autowired
	public TrekService trek_service;
	
	@GetMapping("/treks")
	public ResponseEntity<List<TrekDetailsPojo>> getAllTreks() {
		return new ResponseEntity<List<TrekDetailsPojo>>(trek_service.getAll(),HttpStatus.OK);
	}
	@PostMapping("/trek")
	public ResponseEntity<MessageModel> addTrek(@ModelAttribute TrekDetailsPojo trek, @RequestParam(required = true) MultipartFile file) {
		return trek_service.add(trek, file);
	}
	
	@GetMapping("/trek/img/{id}")
	public ResponseEntity<byte[]> getImg(@PathVariable Long id) {
		return trek_service.getImage(id);
	}
	
	@PutMapping("/trek")
	public ResponseEntity<MessageModel> editTrek(@RequestParam MultipartFile file,@ModelAttribute TrekDetailsPojo trek){
	
		return trek_service.editTrek(trek,file);
	}
	@GetMapping("/trek/{id}")
	public ResponseEntity getTrekById(@PathVariable Long id) {
		return trek_service.getTrekById(id);
	}
}

