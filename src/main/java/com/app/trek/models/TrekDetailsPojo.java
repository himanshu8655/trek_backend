package com.app.trek.models;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "TrekDetails")
public class TrekDetailsPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public String trek_name;

	public String trek_detail;

	@Transient
	public String img_url;

	@Column(length = 1000000)
	public byte[] img;

	public TrekDetailsPojo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	
	public String getTrek_name() {
		return trek_name;
	}

	public void setTrek_name(String trek_name) {
		this.trek_name = trek_name;
	}

	public String getTrek_detail() {
		return trek_detail;
	}

	public void setTrek_detail(String trek_detail) {
		this.trek_detail = trek_detail;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public TrekDetailsPojo(String trek_name, String trek_detail) {
		this.trek_name = trek_name;
		this.trek_detail = trek_detail;
	}

	

}
