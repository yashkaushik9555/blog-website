package com.example.Blog.website.Dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class UserDto {
	  private long id;
	  
	  @NotEmpty
	  private String name;
	  
	  @Email(message = "Email address is not Valid")
	  private String EmailAddress;
	  
	  @NotEmpty(message = "Password cannot be empty")
	    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
	    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}\\[\\]:;<>,.?/~_+-=|\\\\]).{8,15}$", 
	             message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
	    private String password;
	  
	  @Size(min=10,max=10,message="check the mobile number")
	  private String mobileNumber;
	  
	  private String about;
	  
	  private Date date; 
	  
	  private String isActive;
	  
	  @CreatedBy
	  private String createdby;
	  
	  @LastModifiedBy
	  private String modifiedBy;
}
