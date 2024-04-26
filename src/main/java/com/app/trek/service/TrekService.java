package com.app.trek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.trek.models.MessageModel;
import com.app.trek.models.TrekDetailsPojo;
import com.app.trek.repository.TrekRepository;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TrekService {

	@Autowired
	public TrekRepository trek_repo;

	@Autowired
	private HttpServletRequest request;

	public List<TrekDetailsPojo> getAll() {
		String baseUrl = request.getRequestURL().toString();
		String baseUrlWithoutEndpoint = baseUrl.replace(request.getServletPath(), "");
		List<TrekDetailsPojo> treks = trek_repo.findAll();
		treks.parallelStream().forEach(trek -> {
			trek.setImg_url(baseUrlWithoutEndpoint + "/trek/img/" + trek.getId());
			trek.setImg(null);
		});
		return treks;
	}

	public ResponseEntity<MessageModel> add(TrekDetailsPojo trek, MultipartFile file) {
		MessageModel msg;
		if (file == null) {
			msg = new MessageModel("File is empty!");
			return new ResponseEntity<MessageModel>(msg, HttpStatus.BAD_REQUEST);
		}
		try {
			trek.setImg(file.getBytes());
			trek_repo.save(trek);
		} catch (Exception e) {
			msg = new MessageModel("Error processing request");
			return new ResponseEntity<MessageModel>(msg, HttpStatus.BAD_REQUEST);
		}
		msg = new MessageModel("Trek Added Successfully");
		return new ResponseEntity<MessageModel>(msg, HttpStatus.OK);
	}

	public ResponseEntity<byte[]> getImage(Long id) {
		TrekDetailsPojo trek = trek_repo.findById(id).orElse(null);

		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG);
	        
	        return new ResponseEntity<byte[]>(trek.getImg(), headers, HttpStatus.OK);

	}
}
