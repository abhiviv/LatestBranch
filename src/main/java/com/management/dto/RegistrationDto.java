package com.management.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.management.entity.City;
import com.management.entity.Country;
import com.management.entity.State;

public class RegistrationDto {

    private Long Id;
	
	private String name;
	
	private String email;
	
	private String role;
	
	private String phoneno;
	
	private String About;

	private byte[] picByte;
	
	private City city;
	
	private State state;
	
	private String zipCode;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	private Country country;

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

	public String getAbout() {
		return About;
	}

	public void setAbout(String about) {
		About = about;
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

	public byte[] getPicByte() {
		if(picByte==null) {
			return picByte;
		}else {
		return decompressBytes(picByte);
		}
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	// uncompress the image bytes before returning it to the angular application
			public static byte[] decompressBytes(byte[] data) {
				Inflater inflater = new Inflater();
				inflater.setInput(data);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
				byte[] buffer = new byte[1024];
				try {
					while (!inflater.finished()) {
						int count = inflater.inflate(buffer);
						outputStream.write(buffer, 0, count);
					}
					outputStream.close();
				} catch (IOException ioe) {
				} catch (DataFormatException e) {
				}
				return outputStream.toByteArray();
			}

			public String getZipCode() {
				return zipCode;
			}

			public void setZipCode(String zipCode) {
				this.zipCode = zipCode;
			}
			
			
			
	
}
