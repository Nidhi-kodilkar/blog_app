package com.app.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import com.app.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	
	private Integer id;
	@NotEmpty(message = "Name must have some characters")
	@Size(min = 4, message="Username must contain atleast 4 characters")
	private String name;
	
	@NotEmpty
	@Email(message="incorrect Email address")
	private String email;
	@NotEmpty
	@Size(min=8, max = 12,message = "Password must contain minimum 8 chars and maximum 12 chars")
	//@JsonIgnore                 //To Hide Password, but it Will ignore for both GET(Marshalling) and POST(UnMarshalling)
	 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)        //To hide password only for Marshalling
	private String password;
	private String about;
	
	
	private Set<RoleDto> roles = new HashSet<>();

	
	
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + "]";
	}
	
	
}
