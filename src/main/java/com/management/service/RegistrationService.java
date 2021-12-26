package com.management.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.management.common.Role;
import com.management.dto.RegistrationDto;
import com.management.entity.City;
import com.management.entity.Country;
import com.management.entity.Registration;
import com.management.entity.State;
import com.management.mapperDto.RegistrationDtomapper;
import com.management.repository.CityRepository;
import com.management.repository.CountryRepsitory;
import com.management.repository.RegistrationRepository;
import com.management.repository.StateRepository;

@Service
@Transactional
public class RegistrationService {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RegistrationDtomapper registrationDtomapper;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private CountryRepsitory  countryRepsitory;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private  MessageSource messageSource;

	public RegistrationDto getuserdetails() {
		Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUserEmailId = (String) authentication;
		Registration loginusers = registrationRepository.findByEmailIgnoreCase(loggedInUserEmailId);
		return registrationDtomapper.registrationDto(loginusers);
	}

	public Boolean registration(Registration registration) {
		String EmailId = registration.getEmail();
		Registration checkDublicate = registrationRepository.findByEmailIgnoreCase(EmailId);
		if (checkDublicate != null) {
			logger.error(EmailId);
			throw new com.management.exception.CustomeException(messageSource.getMessage("api.error.user.already.registered", null, Locale.ENGLISH), "",
					"", "", "if and issued contact us");
		} else {
			registration.setPassword(securityService.encodePassword(registration.getPassword()));
			Mailthread mailthread=new Mailthread(mailService, registration.getEmail(),registration.getName());
			mailthread.start();
			registrationRepository.save(registration);
			return true;
		}

	}

	public Boolean UserRegistartion(Registration registration) {
		String EmailId = registration.getEmail();
		Registration checkDublicate = registrationRepository.findByEmailIgnoreCase(EmailId);
		if (checkDublicate != null) {
			throw new com.management.exception.CustomeException(messageSource.getMessage("api.error.user.already.registered", null, Locale.ENGLISH), "",
					"", "", "if and issued contact us");
		} else {
			registration.setPassword(securityService.encodePassword(registration.getPassword()));
			Mailthread mailthread=new Mailthread(mailService, registration.getEmail(),registration.getName());
			mailthread.start();
			registration.setRole(Role.ROLE_USER.toString());
			registrationRepository.save(registration);
			return true;
		}
	}

	public Boolean CheckDublicate(String EmailId) {
		Registration checkDublicate = registrationRepository.findByEmailIgnoreCase(EmailId);
		if (checkDublicate != null) {
			return false;
		}
		return true;
	}

	public Registration updateData(Registration registration) {
		Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUserEmailId = (String) authentication;
		Registration checkDublicate = registrationRepository.findByEmailIgnoreCase(loggedInUserEmailId);
		
		if (checkDublicate != null) {
			if(registration.getEmail().isEmpty() || registration.getName().isEmpty()) {
				throw new com.management.exception.CustomeException("Some Fields Are Misssing", "please Fill all * mark fields",
						"", "then click Submit", "if and issued contact us");
			}
			
			if(registration.getCity().getCityId()==0 || registration.getCountry().getCountryId()==0 || registration.getState().getStateId()==0) {
				copy(registration, checkDublicate);
				return registrationRepository.save(checkDublicate);	
			}
			else {
			copydata(registration, checkDublicate);
			System.out.println("=========================="+registration.getEmail());
			
			Country country=countryRepsitory.findByCountryId(registration.getCountry().getCountryId());
			checkDublicate.setCountry(country);
			City city=cityRepository.findByCityId(registration.getCity().getCityId());
			checkDublicate.setCity(city);
			State state=stateRepository.findByStateId(registration.getState().getStateId());
			checkDublicate.setState(state);
			registrationRepository.save(checkDublicate);
			return checkDublicate;
			}
		}
		return checkDublicate;

	}

	private void copy(Registration newregistartion, Registration existing) {
		existing.setName(newregistartion.getName());
		existing.setPhoneno(newregistartion.getPhoneno());
		existing.setEmail(newregistartion.getEmail());
		existing.setAbout(newregistartion.getAbout());
		existing.setZipCode(newregistartion.getZipCode());
	}

	public void copydata(Registration newregistartion, Registration existing) {
		existing.setName(newregistartion.getName());
		existing.setPhoneno(newregistartion.getPhoneno());
		existing.setEmail(newregistartion.getEmail());
		existing.setAbout(newregistartion.getAbout());
		existing.setCountry(newregistartion.getCountry());
		existing.setCity(newregistartion.getCity());
		existing.setState(newregistartion.getState());
		existing.setZipCode(newregistartion.getZipCode());
	}

//	@Cacheable("registration")
	public List<RegistrationDto> filter() {
		return registrationRepository.findAll().stream().map(registrationDtomapper::registrationDto).collect(Collectors.toList());
	}


	
	public org.springframework.http.ResponseEntity.BodyBuilder uploadImage(MultipartFile file) throws IOException {
		Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedInUserEmailId = (String) authentication;
		Registration loginusers = registrationRepository.findByEmailIgnoreCase(loggedInUserEmailId);
		if (loginusers != null) {
			System.out.println("Original Image Byte Size - " + file.getBytes().length);
			loginusers.setPicByte(compressBytes(file.getBytes()));
			registrationRepository.save(loginusers);	
		} 
		return ResponseEntity.status(HttpStatus.OK);
	}

// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();

	}

	

}
