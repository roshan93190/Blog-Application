package com.blog.exceptions;

public class UserNotFoundException extends RuntimeException{

	String resourceName;
	String fieldName;
	Integer fieldValue;
	
	public UserNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.fieldName=fieldName;
		this.fieldValue=fieldValue;
		this.resourceName=resourceName;
	}
	
}
