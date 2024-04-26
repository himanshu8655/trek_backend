package com.app.trek.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.trek.models.TrekPojo;

public interface TrekRepository extends JpaRepository<TrekPojo, Long>{

}
