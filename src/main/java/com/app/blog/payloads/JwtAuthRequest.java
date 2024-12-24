package com.app.blog.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthRequest {
	
	private String username;
	private String password;
}
