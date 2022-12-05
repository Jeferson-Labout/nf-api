package com.jldev.apinf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiNfApplicationTests {

	
	public static void main(String[] args) throws IOException {
		
		System.out.println(fileToByte("c://frela/certificado.pfx"));
		
	}
	
	private static String fileToByte(String caminhoArquivo)throws IOException{
		
		
		byte[] fileContent = Files.readAllBytes(new File(caminhoArquivo).toPath());
		
		return Base64.getEncoder().encodeToString(fileContent);
		
		
	}
}
