 package com.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.CategoryNotFoundException;
import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repo.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CatgoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(addedCategory,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCatgory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.getById(categoryId).orElseThrow(()-> new UserNotFoundException("Category", "Category Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDesc());
		category.setCategoryId(categoryDto.getCategoryId());
		
		Category uddatedcategory = this.categoryRepo.save(category);
		return this.modelMapper.map(uddatedcategory, CategoryDto.class);
	
	}

	@Override
	public void deletCategoryDto(Integer categoryId) {
		Category cat = this.categoryRepo.getById(categoryId).orElseThrow(()-> new CategoryNotFoundException("Category","Categoryid", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategoryDto(Integer categoryId) {
		Category cat = this.categoryRepo.getById(categoryId).orElseThrow(()-> new CategoryNotFoundException("Category", "CategoryId", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCatgories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> allCategoriesDto = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return allCategoriesDto;
	}

}
