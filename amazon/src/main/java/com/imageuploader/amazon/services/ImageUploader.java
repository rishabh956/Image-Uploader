package com.imageuploader.amazon.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
	
	String uploadImage(MultipartFile image) ;
	
	//CompletableFuture<List<String>> allFiles() ;
	List<String> allFiles() ;
	String presignedUrl(String fileName) ;
}
