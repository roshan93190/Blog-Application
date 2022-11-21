package com.blog.exceptions;

public class CategoryNotFoundException extends RuntimeException{
 
	String categoryName;
	String categoryField;
	Integer categoryId;
	
	public CategoryNotFoundException(String categoryField , String categoryName, Integer categoryId) {
		this.categoryName=categoryName;
		this.categoryId=categoryId;
		this.categoryField=categoryField;
	}
	
}
