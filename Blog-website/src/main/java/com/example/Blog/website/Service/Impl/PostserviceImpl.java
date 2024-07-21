package com.example.Blog.website.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Blog.website.Dto.PostDto;
import com.example.Blog.website.Entity.Categories;
import com.example.Blog.website.Entity.Posts;
import com.example.Blog.website.Entity.Users;
import com.example.Blog.website.Exception.APPConstant;
import com.example.Blog.website.Exception.PostResponse;
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
	public ResponseEntity<ResponseUtil> createPost(PostDto PostDto, Long UserId, Long CateId) {
		try {// TODO Auto-generated method stub
			Users user = this.userReps.findById(UserId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "Id", UserId));
			Categories cate = this.cateRepo.findById(CateId)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "CateId", CateId));
			Posts postEntity = this.modelMappper.map(PostDto, Posts.class);
			if (postEntity.getImageName() == null || postEntity.getImageName().isEmpty()) {
				postEntity.setImageName("default.png");
			} else {
				postEntity.setImageName(postEntity.getImageName());

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
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, this.modelMappper.map(savePost, PostDto.class), HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> updatePost(PostDto postDto, Long postId) {
		try {

			Posts postFindById = this.postRespo.findById(postId)
					.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
			postFindById.setModifiedDate(new Date());
			postFindById.setContent(postDto.getContent());
			postFindById.setDescription(postDto.getDescription());
			postFindById.setIsActive(postDto.getIsActive());
			postFindById.setTitle(postDto.getTitle());
			Posts save = this.postRespo.save(postFindById);
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, this.modelMappper.map(save, PostDto.class), HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@Override
	public ResponseEntity<?> getAllPost(int pageNo,int pageSize) {
		try {
			Pageable pageData=PageRequest.of(pageNo, pageSize);
			Page<Posts> dataAccToPage = this.postRespo.findAll(pageData);
			List<PostDto> content=dataAccToPage.stream().filter(obj->"Y".equals(obj.getIsActive()))
			.map(post -> this.modelMappper.map(post, PostDto.class)).collect(Collectors.toList());
			PostResponse postRes=new PostResponse();
			postRes.setContent(content);
			postRes.setTotalPage(dataAccToPage.getTotalElements());
			postRes.setPageSize(dataAccToPage.getSize());
			postRes.setLastPage(dataAccToPage.isLast());
			
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, postRes,
					HttpStatus.FOUND), HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@Override
	public ResponseEntity<ResponseUtil> deletePost(Long postId) {
		// TODO Auto-generated method stub
		try {
			Posts findPostById = this.postRespo.findById(postId)
					.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
			findPostById.setIsActive("N");
			Posts save = this.postRespo.save(findPostById);

			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.SUCCESS_MESSAGE, "Deleted Successfully", HttpStatus.OK), HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@Override
	public ResponseEntity<ResponseUtil> getPostByPostId(Long postId) {
		// TODO Auto-generated method stub
		try {
			Posts findPostById = this.postRespo.findById(postId)
					.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.STATUS_SUCCESS_MESSAGE, findPostById, HttpStatus.FOUND), HttpStatus.FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> getPostByUserId(Long userId) {
		try {
			Users findUserByUserId = this.userReps.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("Post", "user", userId));
			Set<Posts> listOfPosts = this.postRespo.findByUser(findUserByUserId);
			List<Posts> listOfActivePosts = listOfPosts.stream().filter(obj -> "Y".equals(obj.getIsActive()))
					.collect(Collectors.toList());
			if (listOfPosts.size() <= 0) {
				return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
						APPConstant.STATUS_SUCCESS_MESSAGE, "No data found", HttpStatus.NO_CONTENT),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.STATUS_SUCCESS_MESSAGE, listOfActivePosts.stream()
							.map(post -> this.modelMappper.map(post, PostDto.class)).collect(Collectors.toList()),
					HttpStatus.FOUND), HttpStatus.FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@Override
	public ResponseEntity<ResponseUtil> getPostByCategoryId(Long Cateid) {
		try {
			Categories cateFindById = this.cateRepo.findById(Cateid)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "Cateid", Cateid));
			Set<Posts> byCategory = this.postRespo.findByCategory(cateFindById);
			List<Posts> listOfAllActivePost = byCategory.stream().filter(obj -> "Y".equals(obj.getIsActive()))
					.collect(Collectors.toList());
			if (byCategory.size() <= 0) {
				return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
						APPConstant.STATUS_SUCCESS_MESSAGE, "No data found", HttpStatus.NO_CONTENT),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.STATUS_SUCCESS_MESSAGE, listOfAllActivePost.stream()
							.map((obj) -> this.modelMappper.map(obj, PostDto.class)).collect(Collectors.toList()),
					HttpStatus.OK), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> saveListOfPost(List<PostDto> listOfPosts) {
		try {
			Set<Posts> postsSet = listOfPosts.stream().map(obj -> this.modelMappper.map(obj, Posts.class))
					.collect(Collectors.toSet());

			for (Posts post : postsSet) {
				Categories category = post.getCategory();
				Users user = post.getUser();
				long userId = user.getId();
				long cateId = category.getId();

				Users users = this.userReps.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
				Categories cate = this.cateRepo.findById(cateId)
						.orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
                post.setUser(users);
                post.setCategory(cate);
				if (post.getImageName() == null || post.getImageName().isEmpty()) {
					post.setImageName("default.png");
				}
				post.setCreatedDate(new Date());
				
			}
			List<Posts> saveAll = this.postRespo.saveAll(postsSet);
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.SUCCESS_MESSAGE,
					APPConstant.STATUS_SUCCESS_MESSAGE, saveAll.stream()
							.map((obj) -> this.modelMappper.map(obj, PostDto.class)).collect(Collectors.toList()),
					HttpStatus.OK), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseUtil> searchPost(String keyWord) {
		try {
			List<Posts> byTitleContaining = this.postRespo.findByTitleContaining(keyWord);
			return new ResponseEntity<ResponseUtil>(
					new ResponseUtil(APPConstant.SUCCESS_MESSAGE, APPConstant.SUCCESS_MESSAGE,
							byTitleContaining.stream().map(obj -> this.modelMappper.map(obj, PostDto.class))
									.collect(Collectors.toList()),
							HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ResponseUtil>(new ResponseUtil(APPConstant.FAILED_MESSAGE,
					APPConstant.FAILED_MESSAGE, e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
