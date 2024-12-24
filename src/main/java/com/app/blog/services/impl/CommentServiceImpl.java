package com.app.blog.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.logback.RollingPolicySystemProperty;
import org.springframework.stereotype.Service;

import com.app.blog.entities.Comment;
import com.app.blog.entities.Post;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.CommentDto;
import com.app.blog.payloads.PostDto;
import com.app.blog.repositories.CommentRepo;
import com.app.blog.repositories.PostRepo;
import com.app.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDto createComment(Integer postId, CommentDto commentDto) {
		
		//get post by postId
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		//convert commentdto into comment
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment newComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(newComment, CommentDto.class);
	}

	
	
	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentId", commentId));
		
		comment.setComment(commentDto.getComment());
		
		Comment updatedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}



	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment", "commentId", commentId));
		this.commentRepo.delete(comment);
	}

	
}
