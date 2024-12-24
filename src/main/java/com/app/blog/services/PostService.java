package com.app.blog.services;

import java.util.List;

import com.app.blog.entities.Post;
import com.app.blog.payloads.PostDto;
import com.app.blog.payloads.PostResponse;

public interface PostService {
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//get all by using user id
	List<PostDto> getAllPostByUserId(Integer userid);
	//get all by using category name
	List<PostDto> getAllPostByCategory(String categoryName);
	//get all by using category id
	List<PostDto> getAllPostByCategoryId(Integer categoryId);

	//get all
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get single post by using post id
	PostDto getByPostId(Integer postId);
	
	//get single post by using post title
	PostDto getByPostTitle(String title);
	
	//search post
	List<PostDto> search(String keywoord);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void delete(Integer postId);

}
