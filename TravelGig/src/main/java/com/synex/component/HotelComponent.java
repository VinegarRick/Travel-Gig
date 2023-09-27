package com.synex.component;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Component
public class HotelComponent {

	public JsonNode findHotel(String searchString) {
		System.out.println("inside Rest Client");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/searchHotel/" + searchString, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		
		return returnObj;
	}
	
	public JsonNode findUniqueRoomTypeNamesByHotelId(int hotelId) {
		System.out.println("inside Rest Client");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/findUniqueRoomTypeNamesByHotelId/" + hotelId, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		return returnObj;		
	}
	
	public JsonNode findHotelRoomsByHotelId(int hotelId) {
		System.out.println("inside Rest Client");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/findHotelRoomsByHotelId/" + hotelId, Object.class);
		Object objects = responseEntity.getBody();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
				
		return returnObj;		
	}
}






