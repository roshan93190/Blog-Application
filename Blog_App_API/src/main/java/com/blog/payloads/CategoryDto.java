package com.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CategoryDto {

	private Integer categoryId;
	private String categoryTitle;
	@NotEmpty
	@NotNull
	private String categoryDesc;
	
}
