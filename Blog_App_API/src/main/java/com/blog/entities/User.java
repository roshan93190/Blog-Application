package com.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer user_Id;
	
	@Column(name = "user_name", nullable = false, length=100 )
	private String name;
	private String email; 
	private String password;
	private String about;
	
}
