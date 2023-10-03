package com.synex.component;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class BookingComponent {

	public JsonNode saveBooking(JsonNode booking) {
		System.out.println("inside saveBooking of BookingComponent");
		
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
		System.out.println("inside saveGuest of BookingComponent");
		
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
	
	public JsonNode findAllBookingsByUsername(String username) {
		System.out.println("inside findAllBookingsByUsername of BookingComponent");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8484/findAllBookingsByUsername/" + username, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		
		return returnObj;
	}
	
	public JsonNode updateBookingStatus(int bookingId, String status) {
	    System.out.println("inside updateBookingStatus of BookingComponent");

	    ObjectMapper mapper = new ObjectMapper();
	    ObjectNode requestBody = mapper.createObjectNode();
	    requestBody.put("bookingId", bookingId);
	    requestBody.put("status", status);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    HttpEntity<JsonNode> requestEntity = new HttpEntity<>(requestBody, headers);

	    RestTemplate restTemplate = new RestTemplate();

	    ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8484/updateBookingStatus", requestEntity, Object.class);

	    Object objects = responseEntity.getBody();

	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);

	    return returnObj;
	}
	
	public JsonNode saveReview(JsonNode review, int bookingId) {
		System.out.println("inside saveReview of BookingComponent");
		
	    ObjectMapper mapper = new ObjectMapper();
	    ObjectNode requestBody = mapper.createObjectNode();
	    requestBody.put("review", review);
	    requestBody.put("bookingId", bookingId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<JsonNode> requestEntity = new HttpEntity<>(requestBody, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8484/saveReview", requestEntity, Object.class);
		Object objects = responseEntity.getBody();
		
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		return returnObj;
	}
	
	public JsonNode findReviewsByHotelId(int hotelId) {
		System.out.println("inside findReviewsByHotelId of BookingComponent");
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8484/findReviewsByHotelId/" + hotelId, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
		
		return returnObj;
	}
}