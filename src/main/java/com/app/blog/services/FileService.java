package com.app.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
		
		//upload image/file
		String uploadImage(String path, MultipartFile file) throws IOException;
		
		//get image from path/ serve image
		InputStream getResource(String path, String filename) throws FileNotFoundException;
}
