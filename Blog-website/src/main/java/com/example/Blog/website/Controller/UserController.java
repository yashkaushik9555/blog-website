package com.example.Blog.website.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Blog.website.Dto.UserDto;
import com.example.Blog.website.Entity.Users;
import com.example.Blog.website.Exception.APPConstant;
import com.example.Blog.website.Exception.ApiResponse;
import com.example.Blog.website.Exception.ResponseUtil;
import com.example.Blog.website.Repo.UserRepo;
import com.example.Blog.website.Service.UsersService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/userController")
public class UserController {
  @Autowired
  private UsersService userService;
  @Autowired
  private UserRepo userRepo;
  
  @PostMapping("/create")
  public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
	  String email=userDto.getEmailAddress();
	  String mobile=userDto.getMobileNumber();
	  Users findbyemailormobileNumber = this.userRepo.findByEmailAddressOrMobileNumber(email,mobile);
      if (findbyemailormobileNumber!=null) {
          return new ResponseEntity<>(Map.of("message","User already exists with email or mobile"), HttpStatus.CONFLICT);
      }
      else {
	 UserDto createdUser= this.userService.createUser(userDto);
	 return new ResponseEntity<>(createdUser,HttpStatus.CREATED);}
  }
  @PostMapping("/saveListofUsers")
 public  ResponseEntity<ResponseUtil>saveListOfUsers(@RequestBody List<UserDto>listofusers){
	   ResponseEntity<ResponseUtil> saveListOfUsers = this.userService.saveListOfUsers(listofusers);
	   return new ResponseEntity<>(new ResponseUtil(
               APPConstant.SUCCESS_MESSAGE, // Assuming SUCCESS_MESSAGE is defined
               APPConstant.SUCCESS_MESSAGE,
               saveListOfUsers,
               HttpStatus.OK), HttpStatus.OK);
  }
  @PutMapping("/editUser/{userId}")
  ResponseEntity<?>updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Long userId){
	  /*String email=userDto.getEmailAddress();
	  String phn=userDto.getMobileNumber();
	  Users byEmailAddressOrMobileNumber = this.userRepo.findByEmailAddressOrMobileNumber(email, phn);
	  if(byEmailAddressOrMobileNumber!=null) {
		  return new ResponseEntity<>(Map.of("message","Already Exist with the email or mobile"),HttpStatus.CONFLICT);
	  }*/
		/* else { */
		
		  UserDto updateUser = this.userService.updateUser(userDto, userId);
		  return new ResponseEntity<>(updateUser,HttpStatus.OK);
			/* } */
  }
  @DeleteMapping("/deleteUser/{userId}")
  public ResponseEntity<?>deleteUser(@PathVariable("userId")Long userId){
	 this.userService.deleteUser(userId);
	  return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Sucessfully",true),HttpStatus.OK);
  }
  
  @GetMapping("/getAllUser")
  ResponseEntity<List<UserDto>>getAllUsers(){
	  List<UserDto> allUsers = this.userService.getAllUsers();
	  return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.OK);
  }
 @GetMapping("/getUserByUserId/{userId}")
 public ResponseEntity<?>getUserById(@PathVariable("userId") Long userId){
	 return new ResponseEntity<>(this.userService.getUserByUserId(userId),HttpStatus.OK);
 }
}
