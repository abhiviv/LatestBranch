package com.management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.dto.CountryDto;
import com.management.entity.City;
import com.management.entity.Country;
import com.management.entity.State;
import com.management.service.LocationServices;

@RestController
@RequestMapping(path = "/web/addlocation")
public class AddLocationController {
	private static final Logger logger = LoggerFactory.getLogger(AddLocationController.class);
	@Autowired
	private LocationServices locationServices;
	
	
	@PostMapping(path = "/addcountry")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Country saveCountry(@RequestBody Country country) {
		return locationServices.SaveCountry(country);
	}
	
	@PostMapping(path = "/addstate/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public State saveState(@PathVariable("id") Long id,@RequestBody State state) {
		return locationServices.Savestate(id,state);
	}
	
	@PostMapping(path = "/addcity/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public City saveCity(@PathVariable("id") Long id,@RequestBody City city) {
		return locationServices.SaveCity(id,city);
	}
	
	@GetMapping(path = "/getCountryById/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public CountryDto country(@PathVariable("id")Long id) {
		logger.info("ok");
		return locationServices.country(id);
		
	}
}
