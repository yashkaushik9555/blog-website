package com.example.Blog.website.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Blog.website.Dto.UserDto;
import com.example.Blog.website.Entity.Users;
import com.example.Blog.website.Exception.APPConstant;
import com.example.Blog.website.Exception.ResourceNotFoundException;
import com.example.Blog.website.Exception.ResponseUtil;
import com.example.Blog.website.Repo.UserRepo;
import com.example.Blog.website.Service.UsersService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UsersService {
	@Autowired
	private UserRepo userRepo;
    @Autowired
    public ModelMapper modelMapper;
	@Override
	public UserDto createUser(UserDto userdto) {
		Users user = this.dtoToUser(userdto);
		user.setDate(new Date());
		Users save = this.userRepo.save(user);
		return this.userToUserDto(save);
	}

	@Override
	@Transactional
	public UserDto updateUser(UserDto userdto, Long userId) {
		Users userfindbyId = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));
		userfindbyId.setName(userdto.getName());
		userfindbyId.setEmailAddress(userdto.getEmailAddress());
		userfindbyId.setAbout(userdto.getAbout());
		userfindbyId.setMobileNumber(userdto.getMobileNumber());
		userfindbyId.setPassword(userdto.getPassword());
		userfindbyId.setIsActive(userdto.getIsActive());
		Users updateuser = this.userRepo.save(userfindbyId);
		return this.userToUserDto(updateuser);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<Users> getAllUsers = this.userRepo.findAll();
		List<UserDto> ListofUsersDto = getAllUsers.stream().map((user) -> this.userToUserDto(user))
				.collect(Collectors.toList());

		return ListofUsersDto;
	}

	@Override
	public void deleteUser(Long userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "UserId", userId));
		this.userRepo.deleteById(userId);
	}

	public UserDto userToUserDto(Users users) {
		UserDto userdto = this.modelMapper.map(users, UserDto.class);
		/*new UserDto();
		userdto.setId(users.getId());
		userdto.setName(users.getName());
		userdto.setAbout(users.getAbout());
		userdto.setEmailAddress(users.getEmailAddress());
		userdto.setPassword(users.getPassword());
		userdto.setMobileNumber(users.getMobileNumber());
		userdto.setDate(users.getDate());
		userdto.setIsActive(users.getIsActive());*/
		return userdto;
	}

	public Users dtoToUser(UserDto userdto) {
		Users user =this.modelMapper.map(userdto, Users.class);
				/*new Users();
		user.setId(userdto.getId());
		user.setName(userdto.getName());
		user.setAbout(userdto.getAbout());
		user.setEmailAddress(userdto.getEmailAddress());
		user.setPassword(userdto.getPassword());
		user.setMobileNumber(userdto.getMobileNumber());
		user.setDate(userdto.getDate());
		user.setIsActive(userdto.getIsActive());*/
		return user;
	}

	@Override
	public UserDto getUserByUserId(Long userId) {
		try {
			Users findById = this.userRepo.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
			return this.userToUserDto(findById);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	@Transactional
	public ResponseEntity<ResponseUtil> saveListOfUsers(List<UserDto> listOfUserDto) {
	    try {
	        // Filter out existing users based on email or mobile number
	        List<Users> listOfUsers1 = new ArrayList<>();
	        for (UserDto userDto : listOfUserDto) {
	            Users exist = userRepo.findByEmailAddressOrMobileNumber(userDto.getEmailAddress(),
	                    userDto.getMobileNumber());
	            if (exist == null) {
	                // If user does not exist, add to the list for saving
	                Users dtoToUser = dtoToUser(userDto);
	              
	                dtoToUser.setDate(new Date());
	                listOfUsers1.add(dtoToUser);
	            } else {
	                // If user already exists, return failure response immediately
	                String message = "Duplicate email or mobile number found: " + userDto.getEmailAddress() + ", " + userDto.getMobileNumber();
	                return new ResponseEntity<>(new ResponseUtil(
	                        APPConstant.FAILED_MESSAGE,
	                        APPConstant.FAILED_MESSAGE,
	                        message,
	                        HttpStatus.OK), HttpStatus.OK);
	            }
	        }

	        // Save only the non-duplicate users
	        List<Users> savedUsers = userRepo.saveAll(listOfUsers1);

	        // Convert saved Users entities back to UserDto
	        List<UserDto> savedUserDtos = savedUsers.stream()
	                .map(this::userToUserDto) // Assuming userToUserDto method exists
	                .collect(Collectors.toList());

	        // Return success response with saved UserDtos
	        return new ResponseEntity<>(new ResponseUtil(
	                APPConstant.SUCCESS_MESSAGE,
	                APPConstant.SUCCESS_MESSAGE,
	                savedUserDtos,
	                HttpStatus.OK), HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        // Return error response if exception occurs
	        return new ResponseEntity<>(new ResponseUtil(
	                APPConstant.FAILED_MESSAGE,
	                APPConstant.FAILED_MESSAGE,
	                "Something went wrong. Please try again.",
	                HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
