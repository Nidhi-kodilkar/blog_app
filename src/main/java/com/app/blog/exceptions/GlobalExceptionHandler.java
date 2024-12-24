package com.app.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodeFormatter;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.MethodArgumentBuilder;

import com.app.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		String message = ex.getMessage();
		ApiResponse apiResponse= new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgnotValidException(MethodArgumentNotValidException ex){
		
		//store the field names and their corresponding validation error messages.
		Map<String, String> map = new HashMap<>();
		
		//Iterate bindingResult and store fieldname and default message into map
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
		    String fieldName = ((FieldError) error).getField();
		    String defaultMessage = error.getDefaultMessage();
		    map.put(fieldName, defaultMessage);
		}

		//return map that contains error and corresponding message 
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
	}
}
