package com.example.Blog.website.Service;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import com.example.Blog.website.Dto.UserDto;
import com.example.Blog.website.Exception.ResponseUtil;

public interface UsersService {

	public UserDto createUser(UserDto user);
	public UserDto updateUser(UserDto user,Long userId);
	public List<UserDto>getAllUsers();
	public void deleteUser(Long userId);
	public UserDto getUserByUserId(Long userId);
	public ResponseEntity<ResponseUtil>saveListOfUsers(List<UserDto> listOfUsers);
}
