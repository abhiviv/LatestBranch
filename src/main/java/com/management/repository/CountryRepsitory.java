package com.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.management.entity.Country;

@Component
public interface CountryRepsitory extends JpaRepository<Country, Integer> {

	Country findByCountryName(String countryName);
	
	Country findByCountryId(Long countryId);
	
}
