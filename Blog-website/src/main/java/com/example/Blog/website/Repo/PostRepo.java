package com.example.Blog.website.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Blog.website.Entity.Categories;
import com.example.Blog.website.Entity.Posts;
import com.example.Blog.website.Entity.Users;

public interface PostRepo extends JpaRepository<Posts, Long> {

// this is custom finder method to find the all post of the user
  List<Posts>findByUser(Users user);

// this is also a custom finder method to find the all post of the same category
  List<Posts>findByCategory(Categories cate);
}
