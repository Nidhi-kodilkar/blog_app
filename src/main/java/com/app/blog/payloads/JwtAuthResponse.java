package com.app.blog.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {
	
	private String jwtToken;
	private String username;

	
	
}
