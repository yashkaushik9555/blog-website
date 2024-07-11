package com.example.Blog.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Blog.website.Entity.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Long> {

}
