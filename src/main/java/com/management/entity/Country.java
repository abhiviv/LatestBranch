package com.management.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long countryId;

	@Column(name = "country_name")
	@NotBlank
	private String countryName;

  	@OneToMany(
	        mappedBy = "country",
	        cascade = CascadeType.PERSIST,
	        fetch = FetchType.LAZY)
    @JsonIgnore
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
	

	

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryName=" + countryName + ", state=" + state + "]";
	}
	
}
