package com.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionIntecepter extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CustomeException.class)
	public final  ResponseEntity<Object> handleAllExceptions(CustomeException ex){ 
		CustomeExceptionSchema customeExceptionSchema=new CustomeExceptionSchema(
				  ex.getMessage(), ex.getDetails(), ex.getHint(), ex.getNextActions(), ex.getSupport()
				);
		return new ResponseEntity<Object>(customeExceptionSchema, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
}
