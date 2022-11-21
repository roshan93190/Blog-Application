package com.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Comment;

import lombok.Data;

@Data
public class PostDto {

	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	

	private UserDto user;
	
	
	private CategoryDto categories;
	
	private Set<CommentDto> comments = new HashSet<>(); 
}
