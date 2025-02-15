package com.app.blog.services;

import java.util.List;

import com.app.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers(); 
	void deleteUser(Integer userId);
	UserDto registerNewUser(UserDto user);
	
	
}
