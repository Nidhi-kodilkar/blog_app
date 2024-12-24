package com.app.blog.payloads;

import com.app.blog.entities.Post;
import com.app.blog.entities.User;

import lombok.Data;

@Data
public class CommentDto {
	
	private Integer commentId;
	private String comment;
	
	
	private Post post;
}
