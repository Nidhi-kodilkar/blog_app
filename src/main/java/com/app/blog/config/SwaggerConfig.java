package com.app.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.SecurityMarker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	
	//add info related to project
	info = @Info(
			
			title = "Blogging Application : Backend",
			description = "A blogging application",
			contact = @Contact(
					
					name = "Nidhi",
					email = "nkodilkar27@gmail.com"
			)
	),
	//server related info dev, test, prod etc
//	servers = {
//			@Server(
//				description = "Dev",
//				url = "http://localhost:5000"
//			)
//	},
		//for prod jar file
		//servers = { @ Server(description = "prod", url = "http://blogapplication-env.eba-m2w8jrzz.eu-north-1.elasticbeanstalk.com") },
	
	security = @SecurityRequirement(
				name="auth"
			)

)
 //security information
@SecurityScheme(
		
		name = "auth",
		in = SecuritySchemeIn.HEADER,
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
		
	)

@Configuration
public class SwaggerConfig {
	
}
