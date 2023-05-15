package com.example.demo.web.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Base64;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.web.fileUtil.FileParser;
import com.example.demo.web.model.AccountHolder;
import com.example.demo.web.repo.AccountHolderRepo;

@Service
public class AccountHolderService  implements FileParser{

	@Autowired private AccountHolderRepo ahr;
	@Value("${image.path}")
	private String path;
	public URI saveAccount(String name, String surname, File csvFile)
	{
	
		AccountHolder acc = new AccountHolder();
		Scanner fileIn = null;
		URI httpimageLink = null;
		if(csvFile.exists()) {
			try {
				fileIn = new Scanner(csvFile);
				String line = "";
				System.out.println("Reading csv file Table Headers....\n"+ fileIn.nextLine());
				System.out.println("Beginnig to search for csv record matching with account holder");
				while(fileIn.hasNext()) {
					line = fileIn.nextLine();
					StringTokenizer str = new StringTokenizer(line,",");
					if(name.equals(str.nextToken()) && surname.equals(str.nextToken())) {
						System.out.println("Account Matched....\nChecking for image format...");
						String imageFormat = str.nextToken();
						System.out.println("Format : "+imageFormat);
						String base64ImageData = str.nextToken();
						System.out.println("base64ImageData: "+ base64ImageData);
						if(base64ImageData != null) {
							System.out.println("System will now attemp to decode base64ImageData and  convert it to the Physical Image");
							File imageFile = convertCsvDataToImage(name,surname ,base64ImageData,imageFormat);
							System.out.println("\nCreating httpimageLink...");
							httpimageLink = createImageLink(imageFile);
					
						}
						break;
					}
					else {
						System.out.println("Name and Surname was not varified \n looking for another Match");
						continue;
					}
				
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch bloc
				e.printStackTrace();
			}finally {
				fileIn.close();
			}
		}
		acc.setName(name);
		acc.setSurname(surname);
		acc.setHttpImageLink(httpimageLink);
		System.out.println("Attemting to Save account");
		ahr.save(acc);
		
		System.out.println("Account created successful\nSystem will now validate if whether account was create");
		for(AccountHolder a : ahr.findAll()) {
			if(a.getHttpImageLink().equals(acc.getHttpImageLink())){
				System.out.println("Account added "+ a.getName()+"\t"+a.getSurname());
			}
			else {
				System.err.println("An error happened during account creation please try again");
			}
		}
		
		return httpimageLink;
	}
	@Override
	public void parseCSV(File csvFile) {
		// TODO Auto-generated method stub
		System.out.println("Starting File Parser to locate csv File from the file system\n");
		if(csvFile.exists()) {
			System.out.println("csv file was found on Path: "+csvFile.getAbsolutePath());
			System.out.println("Beginning to Process the csv file");
					}
		else {
			System.err.println("File not found on specified path");
		}
	}

	@Override
	public File convertCsvDataToImage(String name,String surname, String base64ImageData,String imageFormat) {
		// TODO Auto-generated method stub
		Thread thread = new Thread();
		String baseHttpLink = "v1apiimage";
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
			System.out.println("Image directory was found on  path : "+file.getAbsolutePath()+"\nDecoding image in progress... ");
			byte[] bytes = Base64.getDecoder().decode(base64ImageData);
			try {
				//BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
				
				File output = new File(file.getAbsoluteFile()+"/"+name+surname);
				OutputStream os = null;
				switch(imageFormat) {
				case "image/jpeg":
					//ImageIO.write(image,"jpg", output);	
					os = new BufferedOutputStream(new FileOutputStream(output+".jpg"));
					os.write(bytes);
					System.out.println("Image created successful");
					return new File(output+".jpg");
					
					
				case "image/png":
					//ImageIO.write(image,"png", output);
					os = new BufferedOutputStream(new FileOutputStream(output+".png"));
					os.write(bytes);
					System.out.println("Image created successful");
					return new File(output+".png");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return null;
	}

	@Override
	public URI createImageLink(File imageFile) {
		// TODO Auto-generated method stub
		URI httpimageLink = imageFile.toURI();
		return httpimageLink;
	}
	
	
}
