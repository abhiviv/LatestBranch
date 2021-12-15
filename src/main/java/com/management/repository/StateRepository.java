package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer>{
 
	
	State findByStateName(String stateName);
	 
	State findByStateId(Long stateId);
}
