package com.example.Blog.website.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.processing.FilerException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uploadImage(String path,MultipartFile file) throws IOException;
	InputStream getResource(String path , String FileName) throws FileNotFoundException ;
}
