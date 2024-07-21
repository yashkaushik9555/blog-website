package com.example.Blog.website.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.Blog.website.Dto.PostDto;
import com.example.Blog.website.Exception.ResponseUtil;

public interface PostService {
	public ResponseEntity<ResponseUtil> createPost(PostDto PostDto,Long userId,Long catId);
	public ResponseEntity<ResponseUtil> updatePost(PostDto PostDto,Long PostId);
	public ResponseEntity<?> getAllPost(int pageNo,int pageSize);
	public ResponseEntity<ResponseUtil> deletePost(Long PostId);
	public ResponseEntity<ResponseUtil> getPostByPostId(Long PostId);
	public ResponseEntity<ResponseUtil> getPostByUserId(Long userId);
	public ResponseEntity<ResponseUtil> getPostByCategoryId(Long Cateid);
	public ResponseEntity<ResponseUtil>saveListOfPost(List<PostDto> listOfPosts);
	public ResponseEntity<ResponseUtil> searchPost(String keyWord);

}
