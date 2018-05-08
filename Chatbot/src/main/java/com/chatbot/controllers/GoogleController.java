package com.chatbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chatbot.models.google.Google;
import com.chatbot.models.google.GoogleDetail;

@RestController
public class GoogleController {
	@Autowired
	public RestTemplate restTemplate;
	
	public Google getAllHotel(String place){
//		String appKey = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=hotels+in+"+ place +"&key=AIzaSyAGiZMLB2OgqOCc53rfzOXL48XXpng8Qtg";
		String appKey = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=hotels+in+"+ place +"&key=AIzaSyDbJ_cmgMOyMQSiRDz5JrUg_qXfuRxSmqs";

		Google hotel = restTemplate.getForObject(appKey, Google.class);
		
		return hotel;
	}
	
	public Google getAllRestaurant(String place){
//		String appKey = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+"+ place +"&key=AIzaSyAGiZMLB2OgqOCc53rfzOXL48XXpng8Qtg";
		String appKey = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+"+ place +"&key=AIzaSyDbJ_cmgMOyMQSiRDz5JrUg_qXfuRxSmqs";

		Google restaurant = restTemplate.getForObject(appKey, Google.class);
		
		return restaurant;
	}
	
	public GoogleDetail getDetailData(String placeId){
//		String appKey = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=AIzaSyAGiZMLB2OgqOCc53rfzOXL48XXpng8Qtg";
		String appKey = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=AIzaSyDbJ_cmgMOyMQSiRDz5JrUg_qXfuRxSmqs";
		GoogleDetail googleDetail = restTemplate.getForObject(appKey, GoogleDetail.class);
		
		return googleDetail;
	}
	
	public String getPhoto(String photo){
		String photoReferences = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photo +"&key=AIzaSyDbJ_cmgMOyMQSiRDz5JrUg_qXfuRxSmqs";
		return photoReferences;
	}
}
