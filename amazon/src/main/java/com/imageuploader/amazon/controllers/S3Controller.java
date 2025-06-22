package com.imageuploader.amazon.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imageuploader.amazon.services.ImageUploader;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/s3")
public class S3Controller {
	
	@Autowired 
	ImageUploader imageUploader ;
	
	@PostMapping()
	public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file)
	{
		return ResponseEntity.ok(imageUploader.uploadImage(file));
	}
	
	@GetMapping()
	public List<String> getAll()
	{
		return this.imageUploader.allFiles();
	}

}
