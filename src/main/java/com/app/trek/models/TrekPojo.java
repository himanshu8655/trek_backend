package com.app.trek.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TrekPojo {
	
	@Id
	public Long id;
	public TrekPojo() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TrekPojo(Long id) {
		super();
		this.id = id;
	}
	

}
