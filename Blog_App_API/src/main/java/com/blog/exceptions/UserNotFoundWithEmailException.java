package com.blog.exceptions;

public class UserNotFoundWithEmailException extends RuntimeException {

	String resourceName;
	String emailName;
	
	public UserNotFoundWithEmailException(String resourceName, String fieldName) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName));
		this.emailName=fieldName;
		this.resourceName=resourceName;
	}
}
