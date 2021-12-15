package com.management.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.dto.CountryDto;
import com.management.entity.City;
import com.management.entity.Country;
import com.management.entity.State;
import com.management.mapperDto.RegistrationDtomapper;
import com.management.repository.CityRepository;
import com.management.repository.CountryRepsitory;
import com.management.repository.StateRepository;

@Service
@Transactional
public class LocationServices {

	@Autowired
	private CountryRepsitory  countryRepsitory;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private RegistrationDtomapper registrationDtomapper;
	
	public Country SaveCountry(Country country) {
	Country country2=countryRepsitory.findByCountryName(country.getCountryName());
	  if(country2 !=null) {
		  throw new com.management.exception.CustomeException("Check Country", "Country Allready used",
					"Enter different CountryName", "then click Submit", "if and issued contact us");  
	  }
	  else {
		  return countryRepsitory.save(country);
	  }
	}
	
	public State Savestate(Long id,State state) {
		State state2=stateRepository.findByStateName(state.getStateName());
		if(state2 != null) {
			throw new com.management.exception.CustomeException("Check State", "State Allready used",
					"Enter different StateName", "then click Submit", "if and issued contact us");  
		}
		Country country2=countryRepsitory.findByCountryId(id);
		state.setCountry(country2);
		stateRepository.save(state);
		return state;
	}
	
	public City SaveCity(Long id,City city) {
		City city2  =cityRepository.findByCityName(city.getCityName());
		if(city2 != null) {
			throw new com.management.exception.CustomeException("Check State", "State Allready used",
					"Enter different StateName", "then click Submit", "if and issued contact us");  
		}
		State state=stateRepository.findByStateId(id);
		city.setState(state);
		cityRepository.save(city);
		return city;
	}
	
	public CountryDto country(Long id) {
		Country country=countryRepsitory.findByCountryId(id);
		return registrationDtomapper.countryDto(country);
	}
	
}
