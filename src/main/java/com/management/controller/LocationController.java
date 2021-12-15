package com.management.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.entity.City;
import com.management.entity.Country;
import com.management.entity.State;
//import com.management.repository.CityRepository;
import com.management.repository.CountryRepsitory;
import com.management.repository.StateRepository;

@RestController
@RequestMapping(path = "/web/location")
public class LocationController {

	
	@Autowired
	private CountryRepsitory  countryRepsitory;
	
//	@Autowired
//	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	
	
	@GetMapping(path = "/getCountry")
	public List<Country> countries(){
		return countryRepsitory.findAll(Sort.by(Sort.Direction.ASC, "countryName")) ;
	}
	
	@GetMapping(path = "/getState/{id}")
	public Set<State> states(@PathVariable("id")Long id){
		Country country=countryRepsitory.findByCountryId(id);
		Comparator<State> comparator = Comparator.comparing(State::getStateName);
		return country.getState().stream().collect(Collectors.toCollection(()-> new TreeSet<>(comparator)));
	            
	}
	
	@GetMapping(path = "/getCity/{id}")
	public Set<City> cities(@PathVariable("id")Long id){
		State state=stateRepository.findByStateId(id);
		if(state!=null){
			return state.getCity();
		}
		else {
			throw new com.management.exception.CustomeException("Value not present", "check value",
					"", "", "");
		}
	}
}
