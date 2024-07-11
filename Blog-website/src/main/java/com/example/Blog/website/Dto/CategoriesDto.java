package com.example.Blog.website.Dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoriesDto {
	
	private long id;
	
	private String description;
	
	@NotEmpty(message = "title should be required")
	private String title;
	
	 private String isActive;
	 
	 private Date date;
	 
	 private Date ModidiedDt;
}
