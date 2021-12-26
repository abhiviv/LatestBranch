package com.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.entity.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	
	Registration findByEmailIgnoreCase(String emailId);
	
	List<Registration> findByRole(String role);

	//Muser findByEmail(String emailId);

}
