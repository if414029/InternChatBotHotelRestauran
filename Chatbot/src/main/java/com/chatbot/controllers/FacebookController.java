package com.chatbot.controllers;

import com.chatbot.models.facebook.Facebook;
import com.chatbot.models.facebook.FacebookAttachment;
import com.chatbot.models.facebook.FacebookButton;
import com.chatbot.models.facebook.FacebookSend;
import com.chatbot.models.facebook.FacebookSendAttachment;
import com.chatbot.models.facebook.FacebookSendButton;
import com.chatbot.models.facebook.FacebookSendButtonMessage;
import com.chatbot.models.facebook.FacebookSendButtonPayload;
import com.chatbot.models.facebook.FacebookSendElement;
import com.chatbot.models.facebook.FacebookSendGenericMessage;
import com.chatbot.models.facebook.FacebookSendMessage;
import com.chatbot.models.facebook.FacebookSendPayload;
import com.chatbot.models.facebook.FacebookSendRecipient;
import com.chatbot.models.facebook.FacebookTemplate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.chatbot.interfaces.EventRepository;
import com.chatbot.interfaces.StateRepository;
import com.chatbot.models.database.Event;
import com.chatbot.models.database.State;
import com.chatbot.models.google.Google;
import com.chatbot.models.google.GoogleDetail;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class FacebookController {
	@Autowired
	public RestTemplate restTemplate;
	@Autowired
	GoogleController googleController;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private EventRepository eventRepository;
	
	ObjectMapper mapper = new ObjectMapper();
	String FACEBOOK_TOKEN = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAaGZBfsEDmYBAJDTDP1ea86Fk3CQuQrHxEPyPcgXbUrVZBxDVaXAu2ZB0yLLuLdCl1zUynsXULWu60hdeXUcUKa3fGSWI3TrsZBXavIcsZAYN0tQqkzPIVWZCmhe2HXKL2j3LMzFKkD4IpdZCt1ZBrFuc3Np2rIhVjVp4FVbm0PJwZDZD";
	
	
	@RequestMapping(value= "/facebookWebhook", method = RequestMethod.GET)
	public String getChallenge(HttpServletRequest request){
		String challenge = request.getParameter("hub.challenge");
		return challenge;
	}
	
	@RequestMapping(value= "/facebookWebhook", method = RequestMethod.POST)
	public void getMessage(@RequestBody Facebook facebook){
		System.out.println(facebook.toString());
		String idUsername = ""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId(); 
		if(stateRepository.findByIdUsername(idUsername) == null){
			Event event = eventRepository.findOne(1);
			State states = new State(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId(), event);
			stateRepository.save(states);
			sendMessageText(facebook);
			sendMessageGenerik(facebook);
		}else{
			Integer idState = stateRepository.findByIdUsername(idUsername).getIdEvent().getIdEvent();
			Integer id = stateRepository.findByIdUsername(idUsername).getIdState().intValue();
			if(idState == 1){
				if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() == null){
					if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getPayload().equalsIgnoreCase("Hotel")){
						sendMessageTextNext(facebook);
						Event eventId = eventRepository.findOne(2);
						stateRepository.editEvent(eventId , id);
					}else if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getPayload().equalsIgnoreCase("Restaurant")){
						sendMessageTextNext(facebook);
						Event eventId = eventRepository.findOne(3);
						stateRepository.editEvent(eventId , id);
					}
				}else if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() != null){
					sendMessageText(facebook);
					sendMessageGenerik(facebook);
				}
			}else if(idState == 2){
				if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() != null){
					Google hotel = googleController.getAllHotel(facebook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
					if(hotel.getResults().isEmpty()){
						sendMessageTextNext(facebook);
					}else{
						sendListHotel(facebook);
						sendMessageButton(facebook);
						Event eventId = eventRepository.findOne(4);
						stateRepository.editEvent(eventId , id);
					}
				}
			}else if(idState == 3){
				if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() != null){
					Google restaurant = googleController.getAllRestaurant(facebook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
					if(restaurant.getResults().isEmpty()){
						sendMessageTextNext(facebook);
					}else{
						sendListRestaurant(facebook);
						sendMessageButton(facebook);
						Event eventId = eventRepository.findOne(5);
						stateRepository.editEvent(eventId , id);
					}
				}
			}else if(idState == 4){
				if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() == null){
					if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getTitle().equalsIgnoreCase("Menu")){
						sendMessageText(facebook);
						sendMessageGenerik(facebook);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getTitle().equalsIgnoreCase("View Review")){
						sendReviewHotel(facebook);
						Event eventId = eventRepository.findOne(6);
						stateRepository.editEvent(eventId , id);
					}
				}else if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() != null){
					sendMessageButton(facebook);
				}
			}else if(idState == 6){
				if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() == null){
					if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getTitle().equalsIgnoreCase("Menu")){
						sendMessageText(facebook);
						sendMessageGenerik(facebook);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else if(facebook.getEntry().get(0).getMessaging().get(0).getMessage().getText() != null){
						sendMessageButton(facebook);
					}
				}
			}else if(idState == 5){
				if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() == null){
					if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getTitle().equalsIgnoreCase("Menu")){
						sendMessageText(facebook);
						sendMessageGenerik(facebook);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else if(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getTitle().equalsIgnoreCase("View Review")){
						sendReviewRestaurant(facebook);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}
				}else if(facebook.getEntry().get(0).getMessaging().get(0).getMessage() != null){
					sendMessageButton(facebook);
				}
			}
		}	
	}	

	public void sendMessageText(Facebook facebook){
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		FacebookSendMessage facebookSendMessage = new FacebookSendMessage("Hai!Selamat Datang,\n"
				+ "Saya EASY,disini kami bisa membantu anda untuk menemukan tempat penginapan"
				+ " serta restoran terdekat"
				+ " dengan memilih menu dibawah ini");
		FacebookSend facebookSend = new FacebookSend(facebookSendRecipient,facebookSendMessage);
		
		HttpEntity<FacebookSend> entity = new HttpEntity<>(facebookSend);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
	}
	
	public void sendMessageTextNext(Facebook facebook){
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		FacebookSendMessage facebookSendMessage = new FacebookSendMessage("Silahkan masukkan kota/daerah yang ingin dicari");
		FacebookSend facebookSend = new FacebookSend(facebookSendRecipient,facebookSendMessage);
		
		HttpEntity<FacebookSend> entity = new HttpEntity<>(facebookSend);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
	}
	
	public void sendMessageButton(Facebook facebook){
		List<FacebookSendButton> facebookSendButtons = new ArrayList<>();
		FacebookSendButton facebookSendButton = new FacebookSendButton("postback", null, "Menu", "Menu");
		facebookSendButtons.add(facebookSendButton);
		FacebookSendButtonPayload facebookSendButtonPayload = new FacebookSendButtonPayload("button", "Pilih Menu untuk kembali", facebookSendButtons);
		FacebookSendAttachment facebookSendButtonAttachment = new FacebookSendAttachment("template", facebookSendButtonPayload);
		FacebookSendButtonMessage facebookSendButtonMessage = new FacebookSendButtonMessage(facebookSendButtonAttachment);
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		FacebookButton facebookButton = new FacebookButton(facebookSendRecipient, facebookSendButtonMessage);
		
		HttpEntity<FacebookButton> entity = new HttpEntity<>(facebookButton);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
	}
	
	public void sendMessageGenerik(Facebook facebook){
		List<FacebookSendButton> facebookSendButtons = new ArrayList<>();
		FacebookSendButton facebookSendButton1 = new FacebookSendButton("postback", null, "Hotel", "Hotel");
		facebookSendButtons.add(facebookSendButton1);
		FacebookSendButton facebookSendButton2 = new FacebookSendButton("postback", null, "Restaurant", "Restaurant");
		facebookSendButtons.add(facebookSendButton2);
		List<FacebookSendElement> facebookSendElements = new ArrayList<>();
		FacebookSendElement facebookSendElement = new FacebookSendElement("EASY","https://www.barcelo.com/en-us/images/385-swimming-pool-4-hotel-barcelo-royal-hideaway-playacar-resort_tcm21-34268_w1600_h611_n.jpg",
				"We are the right answer for everyone!", null, facebookSendButtons);
		facebookSendElements.add(facebookSendElement);
		FacebookSendPayload facebookSendPayload = new FacebookSendPayload("generic", facebookSendElements);
		FacebookAttachment facebookAttachment = new FacebookAttachment("template", facebookSendPayload);
		FacebookSendGenericMessage facebookSendGenericMessage = new FacebookSendGenericMessage(facebookAttachment);
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		FacebookTemplate facebookTemplate = new FacebookTemplate(facebookSendRecipient, facebookSendGenericMessage);
		
		HttpEntity<FacebookTemplate> entity = new HttpEntity<>(facebookTemplate);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);

	}
	
	public void sendListHotel(Facebook facebook){
		List<FacebookSendElement> facebookSendElements = new ArrayList<>();
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		Google hotel = googleController.getAllHotel(facebook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
		
		for(int i=0; i<5; i++){
			if(hotel.getResults().size() == i){
				break;
			}else{
				List<FacebookSendButton> facebookSendButtons = new ArrayList<>();
				GoogleDetail hotelDetail = googleController.getDetailData(hotel.getResults().get(i).getPlace_id());
				
				if(hotelDetail.getResult().getWebsite() != null){
					FacebookSendButton facebookSendButton1 = new FacebookSendButton("web_url", hotelDetail.getResult().getWebsite(), "View", null);
					facebookSendButtons.add(facebookSendButton1);
				}else{
					FacebookSendButton facebookSendButton1 = new FacebookSendButton("web_url", "https://www.google.co.id/search?q="+hotel.getResults().get(i).getName(), "View", null);
					facebookSendButtons.add(facebookSendButton1);
				}
				
				FacebookSendButton facebookSendButton2 = new FacebookSendButton("postback", null, "View Review", hotel.getResults().get(i).getPlace_id());
				facebookSendButtons.add(facebookSendButton2);
				
				FacebookSendElement facebookSendElement = new FacebookSendElement(hotel.getResults().get(i).getName(), googleController.getPhoto(hotel.getResults().get(i).getPhotos().get(0).getPhoto_reference()),
						hotelDetail.getResult().getFormatted_address() + "\n" + hotelDetail.getResult().getFormatted_phone_number(), null, facebookSendButtons);
				facebookSendElements.add(facebookSendElement);
			}
		}
		
		FacebookSendPayload facebookSendPayload = new FacebookSendPayload("generic", facebookSendElements);
		FacebookAttachment facebookAttachment = new FacebookAttachment("template", facebookSendPayload);
		FacebookSendGenericMessage facebookSendGenericMessage = new FacebookSendGenericMessage(facebookAttachment);
		FacebookTemplate facebookTemplate = new FacebookTemplate(facebookSendRecipient, facebookSendGenericMessage);
		
		HttpEntity<FacebookTemplate> entity = new HttpEntity<>(facebookTemplate);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
		
	}
	
	public void sendListRestaurant(Facebook facebook){
		List<FacebookSendElement> facebookSendElements = new ArrayList<>();
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		Google restaurant = googleController.getAllRestaurant(facebook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
		
		for(int i=0; i<5; i++){
			if(restaurant.getResults().size() == i){
				break;
			}else{
				List<FacebookSendButton> facebookSendButtons = new ArrayList<>();
				GoogleDetail restaurantDetail = googleController.getDetailData(restaurant.getResults().get(i).getPlace_id());
				
				if(restaurantDetail.getResult().getWebsite() != null){
					FacebookSendButton facebookSendButton1 = new FacebookSendButton("web_url", restaurantDetail.getResult().getWebsite(), "View", null);	
					facebookSendButtons.add(facebookSendButton1);
				}else{
					FacebookSendButton facebookSendButton1 = new FacebookSendButton("web_url", "https://www.google.co.id/search?q="+restaurant.getResults().get(i).getName(), "View", null);
					facebookSendButtons.add(facebookSendButton1);
				}
				
				FacebookSendButton facebookSendButton2 = new FacebookSendButton("postback", null, "View Review", restaurant.getResults().get(i).getPlace_id());
				facebookSendButtons.add(facebookSendButton2);
				
				FacebookSendElement facebookSendElement = new FacebookSendElement(restaurant.getResults().get(i).getName(), googleController.getPhoto(restaurant.getResults().get(i).getPhotos().get(0).getPhoto_reference()),
						restaurantDetail.getResult().getFormatted_address() + "\n" + restaurantDetail.getResult().getFormatted_phone_number(), null, facebookSendButtons);
				facebookSendElements.add(facebookSendElement);
			}
		}
		
		FacebookSendPayload facebookSendPayload = new FacebookSendPayload("generic", facebookSendElements);
		FacebookAttachment facebookAttachment = new FacebookAttachment("template", facebookSendPayload);
		FacebookSendGenericMessage facebookSendGenericMessage = new FacebookSendGenericMessage(facebookAttachment);
		FacebookTemplate facebookTemplate = new FacebookTemplate(facebookSendRecipient, facebookSendGenericMessage);
		
		HttpEntity<FacebookTemplate> entity = new HttpEntity<>(facebookTemplate);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
	}
	
	public void sendReviewHotel(Facebook facebook){
		List<FacebookSendElement> facebookSendElements = new ArrayList<>();
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		GoogleDetail hotelDetail = googleController.getDetailData(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getPayload());
		
		for(int i=0; i<5; i++){
			if(hotelDetail.getResult().getReviews().size() == i){
				break;
			}else{
				List<FacebookSendButton> facebookSendButtons = new ArrayList<>();
				FacebookSendButton facebookSendButton = new FacebookSendButton("postback", null, "Menu", "Menu");
				facebookSendButtons.add(facebookSendButton);
				FacebookSendElement facebookSendElement = new FacebookSendElement(hotelDetail.getResult().getReviews().get(i).getAuthor_name(), googleController.getPhoto(hotelDetail.getResult().getReviews().get(i).getProfile_photo_url()),
						hotelDetail.getResult().getReviews().get(i).getText(), null, facebookSendButtons);
				facebookSendElements.add(facebookSendElement);
			}
		}
		
		FacebookSendPayload facebookSendPayload = new FacebookSendPayload("generic", facebookSendElements);
		FacebookAttachment facebookAttachment = new FacebookAttachment("template", facebookSendPayload);
		FacebookSendGenericMessage facebookSendGenericMessage = new FacebookSendGenericMessage(facebookAttachment);
		FacebookTemplate facebookTemplate = new FacebookTemplate(facebookSendRecipient, facebookSendGenericMessage);
		
		HttpEntity<FacebookTemplate> entity = new HttpEntity<>(facebookTemplate);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
	}
	
	public void sendReviewRestaurant(Facebook facebook){
		List<FacebookSendElement> facebookSendElements = new ArrayList<>();
		FacebookSendRecipient facebookSendRecipient = new FacebookSendRecipient(""+facebook.getEntry().get(0).getMessaging().get(0).getSender().getId());
		GoogleDetail restaurantDetail = googleController.getDetailData(facebook.getEntry().get(0).getMessaging().get(0).getPostback().getPayload());
		
		for(int i=0; i<5; i++){
			if(restaurantDetail.getResult().getReviews().size() == i){
				break;
			}else{
				List<FacebookSendButton> facebookSendButtons = new ArrayList<>();
				FacebookSendButton facebookSendButton = new FacebookSendButton("postback", null, "Menu", "Menu");
				facebookSendButtons.add(facebookSendButton);
				FacebookSendElement facebookSendElement = new FacebookSendElement(restaurantDetail.getResult().getReviews().get(i).getAuthor_name(), googleController.getPhoto(restaurantDetail.getResult().getReviews().get(i).getProfile_photo_url()),
						restaurantDetail.getResult().getReviews().get(i).getText(), null, facebookSendButtons);
				facebookSendElements.add(facebookSendElement);
			}
		}
		
		FacebookSendPayload facebookSendPayload = new FacebookSendPayload("generic", facebookSendElements);
		FacebookAttachment facebookAttachment = new FacebookAttachment("template", facebookSendPayload);
		FacebookSendGenericMessage facebookSendGenericMessage = new FacebookSendGenericMessage(facebookAttachment);
		FacebookTemplate facebookTemplate = new FacebookTemplate(facebookSendRecipient, facebookSendGenericMessage);
		
		HttpEntity<FacebookTemplate> entity = new HttpEntity<>(facebookTemplate);
		ResponseEntity<String> result = restTemplate.postForEntity(FACEBOOK_TOKEN, entity, String.class);
	}
	
}
