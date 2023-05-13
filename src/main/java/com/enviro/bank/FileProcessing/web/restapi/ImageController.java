package com.example.demo.web.restapi;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.web.service.AccountHolderService;

import jakarta.servlet.http.HttpServlet;

@RestController
@RequestMapping("/v1/api/image")
public class ImageController{
	@Value("${file.path}") private String path;
	@Autowired private AccountHolderService ahs;
	@GetMapping(value = "/{name}/{surname}/{\\w\\.\\w}")
	public ResponseEntity<byte[]> gethttpImageink(@PathVariable String name, @PathVariable String surname) throws InterruptedException, IOException {
		File file = new File(path+"/csvFile.csv");
		ahs.parseCSV(file);
		URI httpimageLink = ahs.saveAccount(name, surname, file);
		File f = new File(httpimageLink);
		FileSystemResource image = new FileSystemResource(f);
		byte[] bytes = StreamUtils.copyToByteArray(image.getInputStream());
		if(httpimageLink.toString().endsWith(".jpg")) {
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		}
		else if(httpimageLink.toString().endsWith(".png")) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
		}
		else {
			return null;
		}
	}
	
}
