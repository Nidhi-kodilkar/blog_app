package com.app.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.blog.entities.Category;
import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.CategoryDto;
import com.app.blog.repositories.CategoryRepo;
import com.app.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

//	@Autowired
//	private Category category;
	@Autowired
	private CategoryRepo repo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto getCategoryDto(Integer id) {
		// TODO Auto-generated method stub
		Category category = this.repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", id));
		
		return this.categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategoryDto() {
		
		List<CategoryDto> dtoList = new ArrayList<>();

		List<Category> categoryList = this.repo.findAll();
		
		for(Category cat: categoryList) {
		
			dtoList.add(this.categoryToCategoryDto(cat));
		}
		
		
		return dtoList;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = this.categoryDtoToCategory(categoryDto);
		Category createdCategory = this.repo.save(category);
		return this.categoryToCategoryDto(createdCategory);
	}

	@Override
	public CategoryDto updateCategoryDto(CategoryDto categoryDto, Integer id) {
		//find category by id
		Category category = this.repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Categoty", "categoryId" ,id));
		
		//update details
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getTitle());
		
		//save updated object into db
		Category updatedCategory =  this.repo.save(category);
		//return updated object
		return this.categoryToCategoryDto(updatedCategory);
	}

	@Override
	public void delete(Integer id) {
		
		this.repo.deleteById(id);

	}

	private CategoryDto categoryToCategoryDto(Category c) {

		CategoryDto dto = this.modelMapper.map(c, CategoryDto.class);
		return dto;
	}

	private Category categoryDtoToCategory(CategoryDto dto) {

		Category category = this.modelMapper.map(dto, Category.class);
		return category;
	}

}
