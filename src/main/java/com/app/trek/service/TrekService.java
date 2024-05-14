package com.app.trek.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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

		List<TrekDetailsPojo> treks = trek_repo.findAll();
		treks.parallelStream().forEach(trek -> {
			trek.setImg_url(getBaseUrl() + "/trek/img/" + trek.getId());
			trek.setImg(null);
		});
		return treks;
	}

	public ResponseEntity<MessageModel> add(@ModelAttribute TrekDetailsPojo trek, MultipartFile file) {
		if (file == null || file.getSize() == 0) {
			return getResponseEntity("File is empty!", HttpStatus.BAD_REQUEST);
		}
		try {
			trek.setImg(file.getBytes());
			trek_repo.save(trek);
		} catch (Exception e) {
			return getResponseEntity("Error processing request", HttpStatus.BAD_REQUEST);
		}
		return getResponseEntity("Trek Added Successfully", HttpStatus.OK);
	}

	public ResponseEntity<byte[]> getImage(Long id) {
		TrekDetailsPojo trek = trek_repo.findById(id).orElse(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return new ResponseEntity<byte[]>(trek.getImg(), headers, HttpStatus.OK);

	}

	public ResponseEntity<MessageModel> editTrek(TrekDetailsPojo new_trek_data, MultipartFile file) {
		try {

			if (new_trek_data.getId() == null)
				return getResponseEntity("Id cannot be null", HttpStatus.BAD_REQUEST);
			TrekDetailsPojo trek_sql = trek_repo.findById(new_trek_data.getId()).orElse(null);
			if (trek_sql == null)
				return getResponseEntity("Trek Id not found!", HttpStatus.BAD_REQUEST);
			if (file == null || file.getSize() == 0)
				new_trek_data.setImg(trek_sql.getImg());

			else
				new_trek_data.setImg(file.getBytes());

			trek_repo.save(new_trek_data);
			return getResponseEntity("File edited Successfully", HttpStatus.OK);
		} catch (IOException e) {
			return getResponseEntity("Error processing file", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity getTrekById(Long id) {
		TrekDetailsPojo trek = trek_repo.findById(id).orElse(null);
		if (trek == null)
			getResponseEntity("No Trek Found!", HttpStatus.NOT_FOUND);
		trek.setImg(null);
		trek.setImg_url(getBaseUrl() + "/trek/img/" + trek.getId());
		return new ResponseEntity<TrekDetailsPojo>(trek, HttpStatus.OK);
	}

	public static ResponseEntity<MessageModel> getResponseEntity(String msg, HttpStatusCode code) {
		return new ResponseEntity<MessageModel>(new MessageModel(msg), code);
	}

	public String getBaseUrl() {
		String url = request.getRequestURL().toString();
		String baseUrl = url.replace(request.getServletPath(), "");
		return baseUrl;
	}
}
