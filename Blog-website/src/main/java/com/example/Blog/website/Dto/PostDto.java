package com.example.Blog.website.Dto;

import java.util.Date;

import com.example.Blog.website.Entity.Categories;
import com.example.Blog.website.Entity.Users;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PostDto {
	@Column(name="post_id")
	private Long id;

	@Column(name="post_content")
	private String content;
	
	@Column(name="post_title")
	private String title;
	
	@Column(name="post_Description")
	private String Description;

	private Date createdDate;

	private Date modifiedDate;
	
	private String isActive;
	
	@Column(name="post_imageName")
	private String imageName;
	
	private UserDto user;
	
	private CategoriesDto category;
}
