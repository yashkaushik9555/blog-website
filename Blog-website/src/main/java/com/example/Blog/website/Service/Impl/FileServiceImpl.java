package com.example.Blog.website.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.processing.FilerException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Blog.website.Service.FileService;

@Service
public class FileServiceImpl implements FileService {


	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
	    String Name = file.getOriginalFilename();
	    String randomId = UUID.randomUUID().toString();
	    String fileName1 = randomId.concat(Name.substring(Name.lastIndexOf(".")));
	    
	    // full path
	    String filePath = path + File.separator + fileName1;
	    
	    File f = new File(filePath).getParentFile();
	    if (!f.exists()) {
	        f.mkdirs(); // This creates all necessary parent directories
	    }
	    
	    Files.copy(file.getInputStream(), Paths.get(filePath));
	    
	    return fileName1;
	}


	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullpath=path+File.separator+fileName;
		InputStream is =new FileInputStream(fullpath);
		return is;
	}

}
