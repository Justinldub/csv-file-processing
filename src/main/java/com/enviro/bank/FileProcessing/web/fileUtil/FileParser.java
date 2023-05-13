package com.example.demo.web.fileUtil;

import java.io.File;
import java.net.URI;

public interface FileParser{
	public void parseCSV(File csvFile);
	public File convertCsvDataToImage(String name,String surname, String base64ImageData,String imageFormat);
	public URI createImageLink(File imageFile); 
}
