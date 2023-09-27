package com.synex.component;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookingComponent {

	public JsonNode saveBooking(JsonNode booking) {
		System.out.println("inside Rest Client");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<JsonNode> requestEntity = new HttpEntity<>(booking, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8484/saveBooking", requestEntity, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		
		return returnObj;
	}
	
	public JsonNode saveGuest(JsonNode guest) {
		System.out.println("inside Rest Client");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<JsonNode> requestEntity = new HttpEntity<>(guest, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8484/saveGuest", requestEntity, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		
		return returnObj;
	}
}
