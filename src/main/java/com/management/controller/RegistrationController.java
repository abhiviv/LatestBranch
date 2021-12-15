package com.management.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.dto.RegistrationDto;
import com.management.entity.Registration;
import com.management.service.RegistrationService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/web")
public class RegistrationController {
 
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
	private RegistrationService registrationService;

	@GetMapping(path = "/getLoginUser")
	public RegistrationDto getuserdetails() {
		return registrationService.getuserdetails();
	}

	@PostMapping(path = "/registration")
	public Boolean registration(@RequestBody Registration registration) {
		return registrationService.UserRegistartion(registration);
	}

	@PostMapping(path = "/Updateregistration")
	public Registration updateRegistration(@RequestBody Registration registration) {
		return registrationService.updateData(registration);
	}

	@GetMapping(path = "/Dublicate/{EmailId}")
	public Boolean CheckDublicate(@PathVariable("EmailId") String EmailId) {
		return registrationService.CheckDublicate(EmailId);
	}

	@PostMapping(path = "/addAdminAndStudent")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Boolean registration1(@RequestBody Registration registration) {
		return registrationService.registration(registration);
	}

	@GetMapping(path = "/getUsersByRoles")
	public List<RegistrationDto> filterData() {
		return registrationService.filter();
	}


	@PostMapping(path="/upload", consumes = { "multipart/form-data" })
	public ResponseEntity uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		registrationService.uploadImage(file);
		return ResponseEntity.ok(HttpStatus.OK);
	}

}
