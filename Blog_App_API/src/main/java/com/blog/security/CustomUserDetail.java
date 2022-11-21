package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.UserNotFoundWithEmailException;
import com.blog.repo.UserRepo;

@Service
public class CustomUserDetail implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

//	Loading user from database by userName
		
		User user = this.userRepo.findByEmail(userName).orElseThrow(()-> new UserNotFoundWithEmailException("User", "email"));
		
		return user;
	}

}
