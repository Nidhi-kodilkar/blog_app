package com.app.blog;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.blog.config.AppConstants;
import com.app.blog.entities.Role;
import com.app.blog.entities.User;
import com.app.blog.repositories.RoleRepo;
import com.app.blog.repositories.UserRepo;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class BlogAppBackendApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("56699878554"));
		System.out.println(this.passwordEncoder.encode("Sham569875"));
		
		try {
	        // Check if roles already exist
	        Optional<Role> adminRole = roleRepo.findById(AppConstants.ADMIN_USER);
	        Optional<Role> normalRole = roleRepo.findById(AppConstants.NORMAL_USER);

	        Role role;
	        if (adminRole.isPresent()) {
	            role = adminRole.get();
	        } else {
	            role = new Role();
	            role.setId(AppConstants.ADMIN_USER);
	            role.setRole_name("ADMIN_USER");
	            role = roleRepo.save(role);
	        }

	        Role role2;
	        if (normalRole.isPresent()) {
	            role2 = normalRole.get();
	        } else {
	            role2 = new Role();
	            role2.setId(AppConstants.NORMAL_USER);
	            role2.setRole_name("NORMAL_USER");
	            role2 = roleRepo.save(role2);
	        }

	        // Create admin user
	        User admin = new User();
	        admin.setName("Admin");
	        admin.setEmail("admin@mail.com");
	        admin.setPassword(passwordEncoder.encode("admin"));
	        admin.setAbout("admin user");

	        Set<Role> roleSet = new HashSet<>();
	        roleSet.add(role);
	        roleSet.add(role2);
	        admin.setRoles(roleSet);

	        // Check if admin user already exists
	        Optional<User> existingAdmin = userRepo.findByEmail("admin@mail.com");
	        if (existingAdmin.isPresent()) {
	            System.out.println("Admin user already exists.");
	        } else {
	            userRepo.save(admin);
	            System.out.println("Admin user created.");
	        }
	    } catch (DataIntegrityViolationException e) {
	        System.out.println("Error creating roles or admin user: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		
}

}