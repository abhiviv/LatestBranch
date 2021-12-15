package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.entity.City;

@Repository
public interface CityRepository  extends JpaRepository<City, Integer>{
	
	City findByCityName(String cityName);	
	
	City findByCityId(Long cityId);

}
