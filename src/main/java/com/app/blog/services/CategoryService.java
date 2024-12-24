package com.app.blog.services;

import java.util.List;

import com.app.blog.entities.Category;
import com.app.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//get by id
	CategoryDto getCategoryDto(Integer id);
	//get all
	List<CategoryDto> getAllCategoryDto();
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	//update
	CategoryDto updateCategoryDto(CategoryDto categoryDto,Integer id);
	//delete
	void delete(Integer id);
}
