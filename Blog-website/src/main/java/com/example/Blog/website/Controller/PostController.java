package com.example.Blog.website.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.aspectj.apache.bcel.classfile.ConstantValue;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Blog.website.Dto.CategoriesDto;
import com.example.Blog.website.Dto.PostDto;
import com.example.Blog.website.Dto.UserDto;
import com.example.Blog.website.Exception.ConstantValues;
import com.example.Blog.website.Exception.ResponseUtil;
import com.example.Blog.website.Service.FileService;
import com.example.Blog.website.Service.PostService;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/create/post")
	 public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
		UserDto user = postDto.getUser();
		CategoriesDto cate = postDto.getCategory();
		long userId = user.getId();
		long Cateid = cate.getId();
		return new ResponseEntity<>(this.postService.createPost(postDto, userId, Cateid), HttpStatus.OK);

	}
	
	@PostMapping("/saveListOfPost")
	 public ResponseEntity<?> saveListOfPost(@RequestBody List<PostDto> postDto) {
		return new ResponseEntity<>(this.postService.saveListOfPost(postDto), HttpStatus.OK);

	}
	
	@PostMapping("/update/{postId}")
	 public ResponseEntity<?> updatePost(@RequestBody PostDto postDto,@PathVariable Long postId) {
		return new ResponseEntity<>(this.postService.updatePost(postDto,postId), HttpStatus.OK);
	}

	@PostMapping("/Delete/{postId}")
	 public ResponseEntity<?> DeletePost(@PathVariable Long postId) {
		return new ResponseEntity<>(this.postService.deletePost(postId), HttpStatus.OK);
	}
	
	@GetMapping("/getPostByUserId/{userId}")
	  public ResponseEntity<?> getPostByUserId(@PathVariable Long userId){
		 return new ResponseEntity<>(this.postService.getPostByUserId(userId),HttpStatus.OK);
	 }
	
	@GetMapping("/getPostByCateId/{cateId}")
	  public ResponseEntity<?> getPostByCateId(@PathVariable Long cateId){
		 return new ResponseEntity<>(this.postService.getPostByCategoryId(cateId),HttpStatus.OK);
	 }
	
		
		@GetMapping("/getAllPost")
		public ResponseEntity<?> getAllPost(
				@RequestParam(value = "pageNumber", defaultValue = ConstantValues.defaultValue, required = false) int pageNumber,
				@RequestParam(value = "pageSize",defaultValue= ConstantValues.defaultValue, required = false) int pageSize) {

			return new ResponseEntity<>(this.postService.getAllPost(pageNumber,pageSize), HttpStatus.OK);
		}
	
	@GetMapping("/getPostByPostId/{postId}")
	  public ResponseEntity<?> getPostByPostId(@PathVariable Long postId){
		 return new ResponseEntity<>(this.postService.getPostByPostId(postId),HttpStatus.OK);
	 }
	@GetMapping("/searchByPostTitle/{keyword}")
	  public ResponseEntity<?> searchByPostTitle(@PathVariable String keyword){
		 return new ResponseEntity<>(this.postService.searchPost(keyword),HttpStatus.OK);
	 }
	
	// post image upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image
			,@PathVariable("postId") long postId) throws IOException{
		ResponseEntity<ResponseUtil> postByPostId = this.postService.getPostByPostId(postId);
		String uploadImage = this.fileService.uploadImage(path, image);
		ResponseUtil body = postByPostId.getBody();
		PostDto postDto = (PostDto) body.getData();
		postDto.setImageName(uploadImage);
		return new ResponseEntity<>(this.postService.updatePost(postDto, postId),HttpStatus.OK);
		
	}
	
	// this code is for serving the image 
	@GetMapping(value="/getImage/{imageName}",produces= MediaType.IMAGE_JPEG_VALUE)
	public void getImageByName(@PathVariable("imageName") String imageName , HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}

}
