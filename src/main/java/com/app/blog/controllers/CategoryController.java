package com.app.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.payloads.CategoryDto;
import com.app.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})@RequestMapping("apis/category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	//Get all category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		
		List<CategoryDto> catlist=this.service.getAllCategoryDto();
		return new ResponseEntity<List<CategoryDto>>(catlist,HttpStatus.OK);
	}
	
	//Get Single category
	@GetMapping("/{categoryid}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryid") Integer id){
		
		CategoryDto cat=this.service.getCategoryDto(id);
		return new ResponseEntity<CategoryDto>(cat,HttpStatus.OK);
	}
	
	//POST
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto){
		
		CategoryDto createdCategoryDto = this.service.createCategory(dto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.CREATED);
				
	}
	
	//PUT
	@PutMapping("/{userid}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto,@PathVariable("userid") Integer id){
		
		CategoryDto updatedcategoryDto = this.service.updateCategoryDto(dto, id);
		return new ResponseEntity<CategoryDto>(updatedcategoryDto, HttpStatus.OK);
	}
	
	//Delete
	
	@DeleteMapping("/{userid}")
    @PreAuthorize("hasRole('ADMIN')")
		public void deleteCategory(Integer id,@PathVariable Integer userid) {
		
		this.service.delete(userid);
	}
}
