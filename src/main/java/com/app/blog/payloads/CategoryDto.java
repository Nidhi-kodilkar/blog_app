package com.app.blog.payloads;


import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
	
	
	
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4, message="Username must contain atleast 4 characters")
	private String title;
	@NotEmpty
	@Size(min = 10, message="description must contain atleast 4 characters")
	private String description;
	
	
}
