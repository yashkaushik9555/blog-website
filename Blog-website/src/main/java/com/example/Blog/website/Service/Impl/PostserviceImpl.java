package com.example.Blog.website.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Blog.website.Dto.PostDto;
import com.example.Blog.website.Entity.Categories;
import com.example.Blog.website.Entity.Posts;
import com.example.Blog.website.Entity.Users;
import com.example.Blog.website.Exception.APPConstant;
import com.example.Blog.website.Exception.ResourceNotFoundException;
import com.example.Blog.website.Exception.ResponseUtil;
import com.example.Blog.website.Repo.CategoriesRepo;
import com.example.Blog.website.Repo.PostRepo;
import com.example.Blog.website.Repo.UserRepo;
import com.example.Blog.website.Service.PostService;

@Service
public class PostserviceImpl implements PostService {
	@Autowired
	private PostRepo postRespo;
	@Autowired
	private ModelMapper modelMappper;
	@Autowired
	private UserRepo userReps;
	
	@Autowired
	private CategoriesRepo cateRepo;

	@Override
	public ResponseEntity<ResponseUtil> createPost(PostDto PostDto,Long UserId,Long CateId) {
		try{// TODO Auto-generated method stub
		Users user=this.userReps.findById(UserId).orElseThrow(()->new ResourceNotFoundException("User", "Id", UserId));
		Categories cate=this.cateRepo.findById(CateId).orElseThrow(()->new ResourceNotFoundException("Category", "CateId", CateId));
		Posts postEntity = this.modelMappper.map(PostDto, Posts.class);
		if(postEntity.getImageName()!=null) {
			postEntity.setImageName(postEntity.getImageName());
		}
		else {
			postEntity.setImageName("default.png");
		}
		postEntity.setIsActive(postEntity.getIsActive());
		postEntity.setDescription(postEntity.getDescription());
		postEntity.setContent(postEntity.getContent());
		postEntity.setCreatedDate(new Date());
		postEntity.setIsActive(postEntity.getIsActive());
		postEntity.setUser(user);
		postEntity.setCategory(cate);
		postEntity.setTitle(postEntity.getTitle());
		Posts savePost = this.postRespo.save(postEntity);
		return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE, this.modelMappper.map(savePost, PostDto.class), HttpStatus.OK),HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE, APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> updatePost(PostDto PostDto, Long PostId) {
	
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> getAllPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> deletePost(Long PostId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> getPostByPostId(Long PostId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> getPostByUserId(Users users) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> saveListOfPost(List<PostDto> listOfPosts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> searchPost(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseUtil> getPostByCategoryId(Long Cateid) {
		try {
			Categories cateFindById = this.cateRepo.findById(Cateid).orElseThrow(()->new ResourceNotFoundException("Category", "Cateid", Cateid));
		List<Posts> byCategory = this.postRespo.findByCategory(cateFindById);
		
		return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,APPConstant.STATUS_SUCCESS_MESSAGE,byCategory.stream().map((obj)->this.modelMappper.map(obj, PostDto.class)).collect(Collectors.toList()),HttpStatus.OK),HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,APPConstant.FAILED_MESSAGE,e,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
