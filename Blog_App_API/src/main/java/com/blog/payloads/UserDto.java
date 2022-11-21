package com.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {

	private Integer user_Id;
	
	@NotEmpty
	@Size(min = 4, message = "user name must be greater or equal 4 characters !!" )
	private String name;
	
	@Email
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	@NotEmpty 
	private String about;
}
