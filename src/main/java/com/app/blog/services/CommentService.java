package com.app.blog.services;

import java.util.List;

import com.app.blog.entities.Comment;
import com.app.blog.payloads.CommentDto;

public interface CommentService {
	
	
	
	//post
	CommentDto createComment(Integer postId, CommentDto commentDto);
	
	//put
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	
	//delete
	void deleteComment(Integer commentId);
	
}
