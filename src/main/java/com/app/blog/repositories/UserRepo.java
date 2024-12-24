package com.app.blog.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.app.blog.entities.Role;
import com.app.blog.entities.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	Set<User> findByRoles(Role role);

	
}
