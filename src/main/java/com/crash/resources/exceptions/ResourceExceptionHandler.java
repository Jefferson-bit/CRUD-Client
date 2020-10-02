package com.crash.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crash.service.exceptions.DataBaseException;
import com.crash.service.exceptions.ResourceNotFound;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<StandardError> notFound(ResourceNotFound ex, HttpServletRequest request){
		StandardError error = new StandardError(
				Instant.now(),
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage(),
				"Resource Not Found",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBaseError(DataBaseException ex, HttpServletRequest request){
		StandardError error = new StandardError(
				Instant.now(),
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage(),
				"Database Error",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}













