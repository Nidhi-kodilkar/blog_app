package com.app.blog.controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.payloads.JwtAuthRequest;
import com.app.blog.payloads.JwtAuthResponse;
import com.app.blog.payloads.UserDto;
import com.app.blog.security.JwtHelper;
import com.app.blog.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

@RestController
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private JwtHelper helper;
	@Autowired
	private UserDetailsService service;
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		
		System.out.println("inside Controller");
		this.doAuthenticate(request.getUsername(), request.getPassword());
		
		System.out.println(request.getUsername());
		System.out.println(request.getPassword());
		

        UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        JwtAuthResponse response = JwtAuthResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	private void doAuthenticate(String email, String password) {
		
		System.out.println("Inside do authenticate method");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
            
            System.out.println("inside doauthenticate method");

        } catch (BadCredentialsException e) {
        	System.out.println("Invalid Username or Password  !!");
            throw new BadCredentialsException(" Invalid Username or Password  !!");
            
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
    @PostMapping("/register")
    private ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
    	
    	UserDto registerUser = this.userService.registerNewUser(userDto);
    	
    	return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
    	
    }
}


