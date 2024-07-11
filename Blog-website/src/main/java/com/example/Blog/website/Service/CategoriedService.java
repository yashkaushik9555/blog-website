package com.example.Blog.website.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.Blog.website.Dto.CategoriesDto;
import com.example.Blog.website.Exception.ResponseUtil;

public interface CategoriedService {
	public ResponseEntity<ResponseUtil> createCategory(CategoriesDto categoryDto);
	public ResponseEntity<ResponseUtil> updateCategory(CategoriesDto categoryDto,Long CategoryId);
	public ResponseEntity<ResponseUtil> getAllCategory();
	public ResponseEntity<ResponseUtil> deleteCategory(Long CategoryId);
	public ResponseEntity<ResponseUtil> getCategoryByCategoryId(Long CategoryId);
	public ResponseEntity<ResponseUtil>saveListOfCategory(List<CategoriesDto> listOfCategory);
}
