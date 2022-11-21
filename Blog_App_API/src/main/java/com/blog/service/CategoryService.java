package com.blog.service;

import java.util.List;

import com.blog.payloads.CategoryDto;


public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCatgory(CategoryDto updateCategory, Integer categoryId);
	
	void deletCategoryDto(Integer categoryId);
	
	CategoryDto getCategoryDto(Integer categoryId);
	
	List<CategoryDto> getAllCatgories();
}
