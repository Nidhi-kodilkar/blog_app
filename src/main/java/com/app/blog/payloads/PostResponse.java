package com.app.blog.payloads;

import java.util.List;

import org.apache.tomcat.jni.Library;

import lombok.Data;

@Data
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private boolean lastPage;
	
}
