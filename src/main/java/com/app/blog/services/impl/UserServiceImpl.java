package com.app.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.blog.config.AppConstants;
import com.app.blog.entities.Role;
import com.app.blog.entities.User;
import com.app.blog.payloads.UserDto;
import com.app.blog.repositories.RoleRepo;
import com.app.blog.repositories.UserRepo;
import com.app.blog.services.UserService;
import com.app.blog.exceptions.*;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = dtoToUser(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User savedUser = this.userRepo.save(user);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub

		// fetching user corresponding to userID and throwing exception if user not
		// found
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		System.out.println("Service layer "+updatedUser);
		// userDto userDtoUpdated = this.userToDto(updatedUser);
		// return userDtoUpdated
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer id) {

		// fetching user corresponding to userID and throwing exception if user not
		// found
		User user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", id));
		
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		System.out.println("Inside get all service imp");
		List<UserDto> dtos = new ArrayList<>();
		
		List<User> users = this.userRepo.findAll();
		
		for (User user : users) {
			
			dtos.add(userToDto(user));
			System.out.println(user.getName());
		}
		
		System.out.println("list"+users);
		System.out.println("dto list" + dtos);
		return dtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		System.out.println("Inside service imp");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
		System.out.println(userId);
		
		this.userRepo.delete(user);

	}
	
	

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = dtoToUser(userDto);
		
		System.out.println(userDto.getPassword());
		
		//encode the password
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		//set role
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User addedUser = this.userRepo.save(user);
		
		return userToDto(addedUser);
	}

	// converting dto to user
	private User dtoToUser(UserDto userDto) {

		
		User user = this.modelMapper.map(userDto, User.class);
		
		//manual way
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		return user;
	}

	// converting user to dto
	private UserDto userToDto(User user) {

		UserDto dto = this.modelMapper.map(user, UserDto.class);
		
		//manual way
//		UserDto dto = new UserDto();
//		dto.setId(user.getId());
//		dto.setName(user.getName());
//		dto.setEmail(user.getEmail());
//		dto.setPassword(user.getPassword());
//		dto.setAbout(user.getAbout());

		return dto;

	}

}
