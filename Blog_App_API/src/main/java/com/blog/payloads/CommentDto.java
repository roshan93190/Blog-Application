package com.blog.payloads;

import lombok.Data;

@Data
public class CommentDto {

	private Integer commentID;
	
	private String contentOfComment;
}
