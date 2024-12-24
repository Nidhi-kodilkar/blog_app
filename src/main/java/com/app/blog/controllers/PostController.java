package com.app.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.blog.config.AppConstants;
import com.app.blog.payloads.PostDto;
import com.app.blog.payloads.PostResponse;
import com.app.blog.services.FileService;
import com.app.blog.services.impl.PostServiceImpl;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RestController
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createdPost = this.postService.createPost(dto, userId, categoryId);

		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer userId) {

		List<PostDto> dtoList = this.postService.getAllPostByUserId(userId);
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Integer categoryId) {

		List<PostDto> dtoList = this.postService.getAllPostByCategoryId(categoryId);
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	@GetMapping("/{categoryTitle}")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("categoryTitle") String categoryTitle) {

		System.out.println(categoryTitle);
		List<PostDto> dtoList = this.postService.getAllPostByCategory(categoryTitle);
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER, required=false) @Parameter(description = "Page number (zero-based)") Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE, required=false) @Parameter(description = "Number of posts per page") Integer pageSize,
			@RequestParam (value = "sortBy", defaultValue=AppConstants.SORT_BY, required=false) @Parameter(description = "Field to sort by") String sortBy,
			@RequestParam (value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) @Parameter(description = "Sort direction (asc or desc)") String sortDir){
		
		System.out.println(pageNumber+" "+pageSize);
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>( postResponse, HttpStatus.OK);
	}

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		System.out.println(postId);
		PostDto dto = this.postService.getByPostId(postId);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	//handler to search using title 
	@GetMapping("/search/{title}")
	public ResponseEntity<List<PostDto>> search(@PathVariable String title){
		
		List<PostDto> dtos = this.postService.search(title);
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	}

	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

		PostDto dto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{postId}")
	public void deletePost(@PathVariable Integer postId) {

		this.postService.delete(postId);
	}
	
	//upload image handler
		@PostMapping("/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadImage(@RequestParam ("image") MultipartFile image,
				@PathVariable Integer postId) {
			
			//get post wrt postId
			PostDto postDto = this.postService.getByPostId(postId);
			
			String filename = "";
			try {
				filename = this.fileService.uploadImage(path, image);
				System.out.println("controller "+filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			postDto.setImageName(filename);
			
			PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
			
		}
		
		
		//handler to serve image
		@GetMapping(value = "/get/image/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(@PathVariable String imagename, HttpServletResponse response) throws IOException {
			
			//read the image from the specified path and filename
			InputStream resource = this.fileService.getResource(path, imagename);
			
			// Set the response content type to "image/jpeg" to indicate that an image is being returned
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			
			// Copy the image data from the InputStream directly to the response output stream
		    // StreamUtils.copy helps transfer the file data efficiently from the input to the output stream
			StreamUtils.copy(resource, response.getOutputStream());
		}
			
		
	
}
















