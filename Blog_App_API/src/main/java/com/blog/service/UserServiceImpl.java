package com.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repo.UserRepo;
import com.blog.exceptions.*;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {

		User updatedUser = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User", "User_id", id)); 
		
		updatedUser.setName(userDto.getName());
		updatedUser.setEmail(userDto.getEmail());
		updatedUser.setPassword(userDto.getPassword());
		updatedUser.setAbout(userDto.getAbout());
		
		User save = this.userRepo.save(updatedUser);
		UserDto updatedUserDto = this.userToDto(updatedUser);
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId ) {
		User  user = this.userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("User", "UserId", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUsers = this.userRepo.findAll();
		List<UserDto> allDtoUsers= allUsers.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return allDtoUsers;
	}

	@Override
	public String deleteUser(Integer id) {
		
		User user = this.userRepo.findById(id).orElseThrow(()-> new UserNotFoundException(" User ", " Id ", id ));
		this.userRepo.delete(user);
		return "User deleted with userId"+" "+id ; 
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setUser_Id(userDto.getUser_Id());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		return user;
		
	}

	public UserDto userToDto(User user) {
		
		UserDto userDto = new UserDto();
		userDto.setUser_Id(user.getUser_Id());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		return userDto;
		
	}

	 
}
