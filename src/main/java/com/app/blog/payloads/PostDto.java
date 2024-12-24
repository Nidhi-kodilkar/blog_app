package com.app.blog.payloads;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.app.blog.entities.Category;
import com.app.blog.entities.Comment;
import com.app.blog.entities.User;
import com.app.blog.repositories.CommentRepo;

import lombok.Data;

@Data
public class PostDto {

	private Integer postId;
	
	private String postTitle;
	
	private String content;
	
	private String imageName;
	
	private Date addeddate;
	
	private UserDto user;
	
	private CategoryDto category;
	
	private List<Comment> comments = new ArrayList<>();
}
