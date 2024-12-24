package com.app.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
			
		//getting orginal file name
		String name = file.getOriginalFilename();
		
		//random name generation for file
		String randomId = UUID.randomUUID().toString();
		String filename1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//set full path
		String filePath = path + File.separator + filename1;
		
		//create folder to save file if not created
		File file2 = new File(path);
		
		if(!file2.exists()) {
			
			file2.mkdir();
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return filename1;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		
		//construct the full path
		String fullPath = path + File.separator + filename;
		
		//to read file create new inputStream to read file content if file isn't present then throw file not found exception
		InputStream iStream = new FileInputStream(fullPath);
		
		return iStream;
	}
	
	
}
