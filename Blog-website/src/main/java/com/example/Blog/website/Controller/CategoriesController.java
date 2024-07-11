package com.example.Blog.website.Controller;

import java.util.List;
import java.util.Map;

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

import com.example.Blog.website.Dto.CategoriesDto;
import com.example.Blog.website.Exception.APPConstant;
import com.example.Blog.website.Exception.ResponseUtil;
import com.example.Blog.website.Service.CategoriedService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoriesController {

	@Autowired
	private CategoriedService categoriesService;
	 @PostMapping("/create")
	  public ResponseEntity<ResponseUtil> createUser(@Valid @RequestBody CategoriesDto categoriesDto){
		 ResponseEntity<ResponseUtil> category = this.categoriesService.createCategory(categoriesDto);
		 return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, category, HttpStatus.CREATED),HttpStatus.CREATED);
	  }
	  @PostMapping("/saveListofcategory")
	 public  ResponseEntity<ResponseUtil>saveListOfCategory(@RequestBody List<CategoriesDto>listofcategoriesDto){
		ResponseEntity<ResponseUtil> saveListOfCategory = this.categoriesService.saveListOfCategory(listofcategoriesDto);
		 return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, saveListOfCategory, HttpStatus.OK),HttpStatus.OK);

	  }
	  @PutMapping("/editcategory/{cateId}")
	  ResponseEntity<ResponseUtil>updateUser(@RequestBody CategoriesDto categoriesDto,@PathVariable("cateId") Long cateId){
		ResponseEntity<ResponseUtil> updateCategory = this.categoriesService.updateCategory(categoriesDto, cateId);
		 return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, updateCategory, HttpStatus.OK),HttpStatus.OK);

	  }
	  @DeleteMapping("/deletecategory/{cateId}")
	  public ResponseEntity<ResponseUtil>deleteUser(@PathVariable("cateId")Long cateId){
		ResponseEntity<ResponseUtil> deleteCategory = this.categoriesService.deleteCategory(cateId);
		 return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, deleteCategory, HttpStatus.OK),HttpStatus.OK);

	  }
	  
	  @GetMapping("/getAllCate")
	  ResponseEntity<ResponseUtil>getAllUsers(){
		ResponseEntity<ResponseUtil> allCategory = this.categoriesService.getAllCategory();
		 return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, allCategory, HttpStatus.OK),HttpStatus.OK);

	  }
	 @GetMapping("/getCategoryByCategoryId/{cateId}")
	 public ResponseEntity<?>getCategoryByCategoryId(@PathVariable("cateId") Long cateId){
		ResponseEntity<ResponseUtil> categoryByCategoryId = this.categoriesService.getCategoryByCategoryId(cateId);
		 return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, categoryByCategoryId, HttpStatus.OK),HttpStatus.OK);

	 }
}
