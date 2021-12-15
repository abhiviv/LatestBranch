package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.management.common.Role;
import com.management.repository.CityRepository;
import com.management.repository.CountryRepsitory;
import com.management.repository.RegistrationRepository;
import com.management.repository.StateRepository;

@SpringBootApplication
@CrossOrigin
@EnableCaching
public class ManagementApplication implements CommandLineRunner{
	
	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Autowired
	private CountryRepsitory countryRepsitory;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
	Registration();
	Country();
	}

	
	public void Country() {
		com.management.entity.Country country=new com.management.entity.Country();
		//country.setCountryId(1L);
		country.setCountryName("India");
		countryRepsitory.save(country);
		State();
	}
	
	
	private void State() {
	     com.management.entity.State state=new com.management.entity.State();
	     //state.setStateId(1L);
	     state.setStateName("Gujarat");
	     com.management.entity.Country country=countryRepsitory.findByCountryId(1L);
	     state.setCountry(country);
	     stateRepository.save(state);
	     City();
	}
	
	private void City() {
	     com.management.entity.City city =new com.management.entity.City();
	     //city.setCityId(1L);
	     city.setCityName("Bharuch");
	     com.management.entity.State state=stateRepository.findByStateId(1L);
	     city.setState(state);
	     cityRepository.save(city);
	}
	
	public void Registration() {
		com.management.entity.Registration registration=new com.management.entity.Registration();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("123");
		registration.setEmail("Admin@gmail.com");
		registration.setName("Abhishek");
		registration.setPhoneno("7016503938");
		registration.setAbout("I'm Yuki. Full Stack Designer I enjoy creating user-centric, delightful and human experiences.");
		registration.setRole(Role.ROLE_ADMIN.toString());
		registration.setPassword(hashedPassword);
		registrationRepository.save(registration);
	}
}
