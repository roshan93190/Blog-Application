package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.payloads.UserDto;


public interface UserService {

	public UserDto createUser(UserDto userDto);
	public UserDto updateUser(UserDto user, Integer id);
	public UserDto getUserById(Integer userId);
	public List<UserDto> getAllUser();
	public String deleteUser(Integer id);
	
	
}
