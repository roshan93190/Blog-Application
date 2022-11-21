package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.serviceImpl.CatgoryServiceImpl;

@RestController
@RequestMapping("/api/categories")
public class CatgoryController {
	
	
	@Autowired
	CatgoryServiceImpl categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto , @PathVariable Integer categoryID){
		
		CategoryDto updatedCategory = this.categoryService.updateCatgory(categoryDto, categoryID);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);		
	}
	
    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
    	this.categoryService.deletCategoryDto(categoryId);
    	return new ResponseEntity<ApiResponse>(new ApiResponse("Category delted successfully",false),HttpStatus.OK);
    }

    @GetMapping("/{catgoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer CategoryId){
    	CategoryDto categoryDto= this.categoryService.getCategoryDto(CategoryId);
    	return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> allCategories(){
      List<CategoryDto> allCat = this.categoryService.getAllCatgories();
      return new ResponseEntity<List<CategoryDto>>(allCat,HttpStatus.ACCEPTED);
    }
    
}
