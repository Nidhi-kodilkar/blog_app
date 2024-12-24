package com.app.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.payloads.CommentDto;
import com.app.blog.services.impl.CommentServiceImpl;


@RestController
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentServiceImpl commentService;
	
	@PostMapping("/{postId}")
	public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId) {
		
		CommentDto dto = this.commentService.createComment(postId, commentDto);
		
		return new ResponseEntity<CommentDto>(dto, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer commentId) {
		
		CommentDto dto = this.commentService.updateComment(commentDto, commentId);
		return  new ResponseEntity<CommentDto>(dto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Integer commentId) {
		
		this.commentService.deleteComment(commentId);
	}
}
