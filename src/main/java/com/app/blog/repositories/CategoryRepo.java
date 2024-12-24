package com.app.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	Category findByTitle(String title);
}
