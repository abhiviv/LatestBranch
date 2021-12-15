package com.management.dto;

import java.util.Set;

import com.management.entity.State;

public class CountryDto {

	
	private Long countryId;
	
	private String countryName;
	
	private Set<State> state;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Set<State> getState() {
		return state;
	}

	public void setState(Set<State> state) {
		this.state = state;
	}
	
	
}
