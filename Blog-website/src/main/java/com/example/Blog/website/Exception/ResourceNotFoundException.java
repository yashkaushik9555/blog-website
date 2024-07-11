package com.example.Blog.website.Exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
  String resourcename;
  String fieldname;
  long fieldId;
private String fieldname2;
private HttpStatus statuscode;
  public ResourceNotFoundException(String resourcename, String fieldname, long fieldId) {
	super(String.format("%s not found with %s :%s", resourcename,fieldname,fieldId));
	this.resourcename = resourcename;
	this.fieldname = fieldname;
	this.fieldId = fieldId;
}
public ResourceNotFoundException(String string, String mobileNumber, String emailAddress, HttpStatus conflict) {
	 this.resourcename=string;
	 this.fieldname=mobileNumber;
	 this.fieldname2=emailAddress;
	 this.statuscode=conflict;
}

}
