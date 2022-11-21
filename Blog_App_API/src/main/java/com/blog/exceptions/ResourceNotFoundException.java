package com.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String fieldName;
	Integer fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.fieldName=fieldName;
		this.fieldValue=fieldValue;
		this.resourceName=resourceName;
	}
	
}
