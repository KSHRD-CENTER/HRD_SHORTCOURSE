package kh.com.kshrd.shortcourse.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.sf.jasperreports.engine.JasperPrint;

@RestController
public class DownloadController {
	
	@RequestMapping(value = "/download/{applicationId}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFile(HttpServletResponse response)
	        throws IOException {

	    ClassPathResource pdfFile = new ClassPathResource("KOK PHENG1471515724049.pdf");

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");
	        
	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentLength(pdfFile.contentLength())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(new InputStreamResource(pdfFile.getInputStream()));
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> fetchFile() throws IOException{
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		
		messageConverters.add(new ByteArrayHttpMessageConverter());
		
		RestTemplate restTemplate = new RestTemplate(messageConverters);
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<byte[]> response = restTemplate.exchange(
				"http://localhost:8080/static/Learning RabbitMQ.pdf",
				HttpMethod.GET,
				entity,
				byte[].class);
		
//		if(response.getStatusCode() == HttpStatus.OK){
//			Files.write(Paths.get("MUY_PSnRcqQ.mp4"), response.getBody());
//		}
		
		
		
		
		return response;
	}
	

}
