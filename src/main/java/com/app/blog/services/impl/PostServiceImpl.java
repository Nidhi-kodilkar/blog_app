package com.app.blog.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.blog.entities.Category;
import com.app.blog.entities.Post;
import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.CategoryDto;
import com.app.blog.payloads.PostDto;
import com.app.blog.payloads.PostResponse;
import com.app.blog.repositories.CategoryRepo;
import com.app.blog.repositories.PostRepo;
import com.app.blog.repositories.UserRepo;
import com.app.blog.services.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddeddate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostByUserId(Integer userid) {

		List<PostDto> dtoList = new ArrayList<>();

		// fetching user for given userid
		User user = this.userRepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userid));

		// finding post according to particular user
		List<Post> postList = this.postRepo.findByUser(user);

		// converting list of post into postdto
		for (Post posts : postList) {

			dtoList.add(this.modelMapper.map(posts, PostDto.class));

		}

		return dtoList;
	}

	@Override
	public List<PostDto> getAllPostByCategoryId(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		List<Post> postList = this.postRepo.findByCategory(category);
		List<PostDto> dtos = new ArrayList<>();

		for (Post posts : postList) {

			dtos.add(this.modelMapper.map(posts, PostDto.class));
		}
		return dtos;
	}

	@Override
	@Transactional

	public List<PostDto> getAllPostByCategory(String categoryTitle) {

		Category category = this.categoryRepo.findByTitle(categoryTitle);

		if (category == null) {
			System.out.println("Category not found with title: " + categoryTitle);
			return new ArrayList<>(); // Return an empty list if category is not found
		}
		List<Post> posts = this.postRepo.findByCategory(category);

		if (posts.isEmpty()) {
			System.out.println("No posts found for category: " + categoryTitle);
			return new ArrayList<>(); // Return empty list if no posts are found
		}

		List<PostDto> dtos = new ArrayList<>();
		for (Post post : posts) {

			dtos.add(this.modelMapper.map(post, PostDto.class));
		}
		return dtos;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			
			sort = Sort.by(sortBy).ascending();
		}
		else {
			
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		
		List<Post> allPost = pagePost.getContent();

//		if (allPost.isEmpty()) {
//
//			return new ArrayList<>();
//		}

		List<PostDto> dtos = new ArrayList<>();

		for (Post post : allPost) {

			dtos.add(this.modelMapper.map(post, PostDto.class));
		}
		
		PostResponse postResponse = new PostResponse();
		//setting postResponse attributes
		postResponse.setContent(dtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	@Transactional
	public PostDto getByPostId(Integer postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto getByPostTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> search(String keyword) {
		
		List<Post> posts = this.postRepo.findByPostTitleContaining(keyword);
		List<PostDto> postDtos = new ArrayList<>();
		
		for(Post p: posts) {
			
			postDtos.add(this.modelMapper.map(p, PostDto.class));
		}
		return postDtos;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		
		post.setPostTitle(postDto.getPostTitle());
		
		post.setContent(postDto.getContent());
		
		post.setImageName(postDto.getImageName());
		
		//post.setImageName(postDto.getImageName());
		
		Post updatePost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void delete(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);

	}

}
