package com.example.Blog.website.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.Blog.website.Entity.Users;
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<Users, Long> {

    
    Users findByEmailAddressOrMobileNumber(String emailaddress,String mmobileNumber);
}
