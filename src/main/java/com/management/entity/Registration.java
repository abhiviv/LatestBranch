package com.management.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Registration implements Muser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long Id;
	
	private String name;
	
	@NotNull
	@NotEmpty(message = "{email.notempty}")
	private String email;
	
	@NotNull
	@NotEmpty(message = "{password.notempty}")
	private String password;
	
	private String role;
	
	private String phoneno;
	
	private String About;
	
	private String zipCode;
	
	@Column(name = "picByte", length = 100000)
	private byte[] picByte;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cityId")
	private City city;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "stateId")
	private State state;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "countryId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Country country;
	

	@OneToMany(
	        mappedBy = "registration",
	        cascade = CascadeType.PERSIST,
	        fetch = FetchType.LAZY)
    private Set<ImagesTable> imagesTables;
	
	
	public String ismailSend = "N";
	
	public Registration(byte[] picByte) {
		 this.picByte = picByte;
	}

	public Registration() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneno == null) ? 0 : phoneno.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registration other = (Registration) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneno == null) {
			if (other.phoneno != null)
				return false;
		} else if (!phoneno.equals(other.phoneno))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	

	public String getAbout() {
		return About;
	}

	public void setAbout(String about) {
		About = about;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getIsmailSend() {
		return ismailSend;
	}

	public void setIsmailSend(String ismailSend) {
		this.ismailSend = ismailSend;
	}

	public Set<ImagesTable> getImagesTables() {
		return imagesTables;
	}

	public void setImagesTables(Set<ImagesTable> imagesTables) {
		this.imagesTables = imagesTables;
	}
	
	
}
