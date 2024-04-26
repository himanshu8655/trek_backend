package com.app.trek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.trek.models.TrekDetailsPojo;

public interface TrekRepository extends JpaRepository<TrekDetailsPojo, Long>{

}
