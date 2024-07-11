package com.example.Blog.website.Exception;

import org.springframework.http.HttpStatus;

public class ResponseUtil {
	
	private String status;
	
	private String message;
	
	private Object data;
	
	private HttpStatus httpStatus;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ResponseUtil(String status, String message, Object data, HttpStatus httpStatus) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.httpStatus = httpStatus;
	}
	
	
	

}
