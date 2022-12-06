package com.jldev.apinf.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import br.com.swconsultoria.nfe.util.ObjetoUtil;

public class ArquivoUtil {

	
	
public static String descompactaXml(byte[] gZip)throws IOException{
		
		if(!ObjetoUtil.verifica(gZip).isPresent()) {
			return "";
		}
		
		
		try (final GZIPInputStream gzipInput = new GZIPInputStream(new ByteArrayInputStream(gZip));
				final StringWriter stringWriter = new StringWriter()){
			IOUtils.copy(gzipInput, stringWriter);
			return stringWriter.toString();
			
		}
		
	
	}
	
	
	
	
	public static byte[] compactaXml(String sXml)throws IOException{
		
		if(!ObjetoUtil.verifica(sXml).isPresent()) {
			return null;
		}
		
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		try (OutputStream outStream = new GZIPOutputStream(obj)){
			outStream.write(sXml.getBytes(StandardCharsets.UTF_8));
		}
		
		return obj.toByteArray();
	}
}
