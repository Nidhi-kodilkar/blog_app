package com.app.blog.security;

import java.io.IOException;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtHelper jwtHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = null;
		String username = null;

		if (request.getRequestURI().equals("/auth/login")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
		// 1. get JWT token from request

		String requestToken = request.getHeader("Authorization");

		System.out.println("requestToken " +requestToken);
		// token format - 'Bearer 7854ajkl'

		if (requestToken != null && requestToken.startsWith("Bearer ")) {

			token = requestToken.substring(7);

			try {
				username = this.jwtHelper.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT");
			}
		} else {
			System.out.println("JWT token does not begin with Bearer");
		}

		// once we get token validate that token
		// verifying if there is no Authentication object in the SecurityContext,
		// indicating that no user is currently authenticated.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtHelper.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				
				System.out.println("Invalid jwt token");
			}
		} else {
			
			System.out.println("Username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}
	
	
}
