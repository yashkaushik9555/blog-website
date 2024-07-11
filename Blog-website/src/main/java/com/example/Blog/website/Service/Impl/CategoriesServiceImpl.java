package com.example.Blog.website.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Blog.website.Dto.CategoriesDto;
import com.example.Blog.website.Entity.Categories;
import com.example.Blog.website.Exception.APPConstant;
import com.example.Blog.website.Exception.ResourceNotFoundException;
import com.example.Blog.website.Exception.ResponseUtil;
import com.example.Blog.website.Repo.CategoriesRepo;
import com.example.Blog.website.Service.CategoriedService;

@Service
public class CategoriesServiceImpl implements CategoriedService {
	@Autowired
	private CategoriesRepo categoriesRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<ResponseUtil> createCategory(CategoriesDto categoryDto) {
		try {
			Categories CateEntity = this.modelMapper.map(categoryDto, Categories.class);
			CateEntity.setDate(new Date());
			CateEntity.setDescription(categoryDto.getDescription());
			CateEntity.setIsActive(categoryDto.getIsActive());
			CateEntity.setTitle(categoryDto.getTitle());
			Categories save = this.categoriesRepo.save(CateEntity);

			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, this.modelMapper.map(save, CategoriesDto.class), HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.STATUS_FAIL_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> updateCategory(CategoriesDto categoryDto, Long CategoryId) {
		// TODO Auto-generated method stub
		try {
			Categories cateEntity = this.modelMapper.map(categoryDto, Categories.class);
			Categories findById = this.categoriesRepo.findById(CategoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", CategoryId));
			findById.setDescription(categoryDto.getDescription());
			findById.setIsActive(categoryDto.getIsActive());
			findById.setTitle(categoryDto.getTitle());
			findById.setModidiedDt(new Date());
			this.categoriesRepo.save(findById);
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, this.modelMapper.map(findById, CategoriesDto.class), HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.STATUS_FAIL_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<ResponseUtil> getAllCategory() {
		try {
			List<Categories> listOfAllcategproies = this.categoriesRepo.findAll();
			List<CategoriesDto> collect = listOfAllcategproies.stream()
					.map((cate) -> this.modelMapper.map(cate, CategoriesDto.class)).collect(Collectors.toList());
			return new ResponseEntity<ResponseUtil>(
					new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, collect, HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> deleteCategory(Long CategoryId) {
		try {
			Categories findById = this.categoriesRepo.findById(CategoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", CategoryId));
			this.categoriesRepo.deleteById(CategoryId);
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, "categories Deleted Successfully", HttpStatus.OK), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<ResponseUtil> getCategoryByCategoryId(Long CategoryId) {
		try {
			Categories findById = this.categoriesRepo.findById(CategoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", CategoryId));
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, this.modelMapper.map(findById, CategoriesDto.class), HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> saveListOfCategory(List<CategoriesDto> listOfCategory) {
		try {
			List<Categories> collect = listOfCategory.stream().map(cate -> {
				Categories category = this.modelMapper.map(cate, Categories.class);
				category.setDate(new Date());
				return category;
			}).collect(Collectors.toList());
			List<Categories> saveAll = this.categoriesRepo.saveAll(collect);

			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.STATUS_SUCCESS_MESSAGE, saveAll.stream()
							.map((e -> this.modelMapper.map(e, CategoriesDto.class))).collect(Collectors.toList()),
					HttpStatus.OK), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
