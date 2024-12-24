package com.app.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.blog.security.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JWTAuthenticationEntryPoint point;
	@Autowired
	private JwtAuthenticationFilter filter;
	@Autowired
	private CustomUserDetailService userDetailService;

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		 
		return builder.getAuthenticationManager();
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails userDetails = User.builder().
//                username("DURGESH")
//                .password(passwordEncoder().encode("DURGESH")).roles("ADMIN").
//                build();
//        return new InMemoryUserDetailsManager(userDetails);
//
//		// creating obj of implementation class of UserDetailService
////		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(normalUser, adminUser);
////		return inMemoryUserDetailsManager;
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
//		// Enable CORS
//	    httpSecurity.cors(cors -> cors.configurationSource(request -> {
//	        var config = new org.springframework.web.cors.CorsConfiguration();
//	        config.setAllowedOrigins(List.of("http://localhost:8080")); // Allow your Swagger UI origin
//	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//	        config.setAllowedHeaders(List.of("*"));
//	        config.setAllowCredentials(true);
//	        return config;
//	    }));
		
		// Disable CSRF (cross-site request forgery) as JWT is stateless
	    httpSecurity.csrf(csrf -> csrf.disable());

	    // Configure authorization rules
	    httpSecurity.authorizeHttpRequests(auth -> auth
	            .requestMatchers("/auth/login", "/auth/register", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**","/webjars/**").permitAll() // Allow public access to /auth/login
	            .requestMatchers(HttpMethod.GET).permitAll()
	            .anyRequest().authenticated() // Require authentication for all other requests
	    );

	    // Exception handling for unauthorized access attempts
	    httpSecurity.exceptionHandling(ex -> ex.authenticationEntryPoint(point));

	    // Session management to make it stateless (no session stored)
	    httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	    // Add the custom JWT filter before the UsernamePasswordAuthenticationFilter
	    httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	    return httpSecurity.build();

	}
}
