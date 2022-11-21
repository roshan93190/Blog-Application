package com.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {

	private Integer user_Id;
	private String name;
	private String email;
	private String password;
	private String about;
}
