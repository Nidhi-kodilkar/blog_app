package com.app.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.payloads.UserDto;
import com.app.blog.services.UserService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	
	//POST-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	//PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer id, BindingResult result){
		
		UserDto updatedDto = this.userService.updateUser(userDto, id);
		return ResponseEntity.ok(updatedDto);
		
	}
	
	//Get - get user
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userid") Integer id) {
	
		UserDto getUserDto = this.userService.getUserById(id);
		return ResponseEntity.ok(getUserDto);
	}
	
	//get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		System.out.println("Inside get controller");
		List<UserDto> getUserDto = this.userService.getAllUsers();
		
		return ResponseEntity.ok(getUserDto);
	}
	
	//delete-delete user
	@Hidden //to hide apis on openui
	@DeleteMapping("/{userid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDto> deleteUser(@PathVariable("userid") Integer id){
		
		System.out.println("Inside controller");
		this.userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
