package com.chatbot.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.chatbot.interfaces.EventRepository;
import com.chatbot.interfaces.StateRepository;
import com.chatbot.models.database.Event;
import com.chatbot.models.database.State;
import com.chatbot.models.google.Google;
import com.chatbot.models.google.GoogleDetail;
import com.chatbot.models.kaskus.Kaskus;
import com.chatbot.models.kaskus.KaskusSend;
import com.chatbot.models.kaskus.KaskusSendGeneric;
import com.chatbot.models.kaskus.KaskusSendGenericBody;
import com.chatbot.models.kaskus.KaskusSendGenericButton;
import com.chatbot.models.kaskus.KaskusSendGenericInteractive;
import com.chatbot.models.kaskus.KaskusSendGenericSendList;
import com.chatbot.models.kaskus.KaskusSendList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class KaskusController {
	ObjectMapper mapper = new ObjectMapper();
	private String email = "@kaskus-s.obrol.id";
	String kaskusKey = "https://Hotel and Restaurant:Alliswell19@api.obrol.id/api/v1/bot/send-mass";
	Map<String, String> map = new HashMap<String, String>();
	
	@Autowired
	public RestTemplate restTemplate;
	
	@Autowired
	GoogleController googleController;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private EventRepository eventRepository;
	
	@RequestMapping(value= "/kaskusWebhook", method = RequestMethod.GET)
	public String getChallenge(HttpServletRequest request){
		String challenge = request.getParameter("hub.challenge");
		return challenge;
	}
	
	@RequestMapping(value= "/kaskusWebhook", method = RequestMethod.POST)
	public void getMessage(@RequestBody Kaskus kaskus){
		System.out.print(kaskus.toString());
		if(stateRepository.findByIdUsername(kaskus.getFromPlain()) == null){
			Event event = eventRepository.findOne(1);
			State states = new State(kaskus.getFromPlain(), event);
			stateRepository.save(states);
			sendMessageText(kaskus);
			sendMessageGeneric(kaskus);
		}else{
			Integer idState = stateRepository.findByIdUsername(kaskus.getFromPlain()).getIdEvent().getIdEvent();
			Integer id = stateRepository.findByIdUsername(kaskus.getFromPlain()).getIdState().intValue();
			if(idState == 1){
				if(kaskus.getBody().equalsIgnoreCase("Hotel")){
					sendMessageTextNext(kaskus, idState);
					Event eventId = eventRepository.findOne(2);
					stateRepository.editEvent(eventId , id);
				}else if(kaskus.getBody().equalsIgnoreCase("Restaurant")){
					sendMessageTextNext(kaskus, idState);
					Event eventId = eventRepository.findOne(3);
					stateRepository.editEvent(eventId , id);
				}else{
					sendMessageText(kaskus);
					sendMessageGeneric(kaskus);
				}
			}else if(idState == 2){		
				Google hotel = googleController.getAllHotel(kaskus.getBody().toString());
				if(hotel.getResults().isEmpty()){
					sendMessageTextNext(kaskus, idState);
				}else{
					sendListHotel(kaskus);
					sendMessageTextMenu(kaskus);
					sendMessageMenu(kaskus);
					Event eventId = eventRepository.findOne(4);
					stateRepository.editEvent(eventId , id);
				}
			}else if(idState == 4){
				if(map.get(kaskus.getBody()) != null){
					sendReviewHotel(kaskus);
					Event eventId = eventRepository.findOne(6);
					stateRepository.editEvent(eventId , id);
				}else if(kaskus.getBody().equalsIgnoreCase("Menu")){
					sendMessageText(kaskus);
					sendMessageGeneric(kaskus);
					Event eventId = eventRepository.findOne(1);
					stateRepository.editEvent(eventId , id);
				}
				else{
					sendMessageTextNext(kaskus, idState);
					sendMessageTextMenu(kaskus);
					sendMessageMenu(kaskus);
				}
			}else if(idState == 3){
				Google restaurant = googleController.getAllRestaurant(kaskus.getBody().toString());
				if(restaurant.getResults().isEmpty()){
					sendMessageTextNext(kaskus, idState);
				}else{
					sendListRestaurant(kaskus);
					sendMessageTextMenu(kaskus);
					sendMessageMenu(kaskus);
					Event eventId = eventRepository.findOne(5);
					stateRepository.editEvent(eventId , id);
				}
			}else if(idState == 5){
				if(map.get(kaskus.getBody()) != null){
					sendReviewRestaurant(kaskus);
					Event eventId = eventRepository.findOne(6);
					stateRepository.editEvent(eventId , id);
				}else if(kaskus.getBody().equalsIgnoreCase("Menu")){
					sendMessageText(kaskus);
					sendMessageGeneric(kaskus);
					Event eventId = eventRepository.findOne(1);
					stateRepository.editEvent(eventId , id);
				}else{
//					sendMessageTextNext(kaskus, idState);
					sendMessageTextMenu(kaskus);
					sendMessageMenu(kaskus);
				}
			}else if(idState == 6){
				if(kaskus.getBody().equalsIgnoreCase("Menu")){
					sendMessageText(kaskus);
					sendMessageGeneric(kaskus);
					Event eventId = eventRepository.findOne(1);
					stateRepository.editEvent(eventId , id);
				}else{
					sendMessageTextMenu(kaskus);
					sendMessageMenu(kaskus);
				}
			}
		}
	}
	
	public void sendMessageText(Kaskus kaskus){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+email, "Hai!Selamat Datang,\n"
				+ "Saya EASY,disini kami bisa membantu anda untuk menemukan tempat penginapan"
				+ " serta restoran terdekat"
				+ " dengan memilih menu dibawah ini");
		kaskusSendLists.add(kaskusSendList);
		KaskusSend kaskusSend = new KaskusSend(147491, kaskusSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
	}
	
	public void sendMessageTextMenu(Kaskus kaskus){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+email, "Pilih Menu untuk kembali");
		kaskusSendLists.add(kaskusSendList);
		KaskusSend kaskusSend = new KaskusSend(147491, kaskusSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
	}
	
	public void sendMessageGeneric(Kaskus kaskus){
		List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
		KaskusSendGenericButton kaskusSendGenericButton1 = new KaskusSendGenericButton("Hotel", "Hotel", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton1);
		KaskusSendGenericButton kaskusSendGenericButton2 = new KaskusSendGenericButton("Restaurant", "Restaurant", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton2);
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, "https://www.barcelo.com/en-us/images/385-swimming-pool-4-hotel-barcelo-royal-hideaway-playacar-resort_tcm21-34268_w1600_h611_n.jpg",
				"EASY", "We are the right answer for everyone!");
		kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+email,
				"Bot Hotel and Restaurant", "Insert your response");
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(147491, kaskusSendGenericSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
		
	}
	
	public void sendMessageMenu(Kaskus kaskus){
		List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
		KaskusSendGenericButton kaskusSendGenericButton = new KaskusSendGenericButton("Menu", "Menu", "recipient");
		kaskusSendGenericButtons.add(kaskusSendGenericButton);
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, null, null, null);
		kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+email,
				"Bot Hotel and Restaurant", "Insert your response");
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(147491, kaskusSendGenericSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
		
	}
	
	public void sendMessageTextNext(Kaskus kaskus, Integer state){
		List<KaskusSendList> kaskusSendLists = new ArrayList<>();
		
		if(state == 2){
			KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+email, "Maaf data yang anda cari tidak ditemukan!"+"\n"+"Silahkan masukkan kembali kota yang ingin dicari");
			kaskusSendLists.add(kaskusSendList);
		}else if(state == 4){
			KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+email, "Maaf, saat ini EASY hanya bisa melakukan ini.");
			kaskusSendLists.add(kaskusSendList);
		}else{
			KaskusSendList kaskusSendList = new KaskusSendList(kaskus.getFromPlain()+email, "Silahkan masukkan kota yang ingin dicari");
			kaskusSendLists.add(kaskusSendList);
		}

		KaskusSend kaskusSend = new KaskusSend(147491, kaskusSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<KaskusSend> entity1 = new HttpEntity<>(kaskusSend,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
	}
	
	public void sendListHotel(Kaskus kaskus){
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		Google hotel = googleController.getAllHotel(kaskus.getBody().toString());

		for(int i=0; i<5; i++){
			if(hotel.getResults().size() == i){
				break;
			}else{
				GoogleDetail hotelDetail = googleController.getDetailData(hotel.getResults().get(i).getPlace_id());
				List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
				KaskusSendGenericButton kaskusSendGenericButton1 = new KaskusSendGenericButton(hotelDetail.getResult().getWebsite(), "View", "recipient");
				kaskusSendGenericButtons.add(kaskusSendGenericButton1);
				KaskusSendGenericButton kaskusSendGenericButton2 = new KaskusSendGenericButton(hotel.getResults().get(i).getName(),"View Review", "recipient");
				kaskusSendGenericButtons.add(kaskusSendGenericButton2);
				KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, googleController.getPhoto(hotel.getResults().get(i).getPhotos().get(0).getPhoto_reference()), 
						hotel.getResults().get(i).getName(), hotelDetail.getResult().getFormatted_address() + "\n" + hotelDetail.getResult().getFormatted_phone_number());
				kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
				map.put(hotel.getResults().get(i).getName(), hotel.getResults().get(i).getPlace_id());
			}
		}
		
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+email,
				"Bot Hotel and Restaurant", "Insert your response");
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(147491, kaskusSendGenericSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
		
	}
	
	public void sendReviewHotel(Kaskus kaskus){
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		
		GoogleDetail hotelDetail = googleController.getDetailData(map.get(kaskus.getBody()));

		for(int i=0; i<5; i++){
			List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
			KaskusSendGenericButton kaskusSendGenericButton = new KaskusSendGenericButton("Menu", "Menu", "recipient");
			kaskusSendGenericButtons.add(kaskusSendGenericButton);
			KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, 
					hotelDetail.getResult().getReviews().get(i).getProfile_photo_url(), hotelDetail.getResult().getReviews().get(i).getAuthor_name(), hotelDetail.getResult().getReviews().get(i).getText());
			kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		}
		
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+email,
				"Bot Hotel and Restaurant", "Insert your response");
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(147491, kaskusSendGenericSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
		
	}
	
	public void sendListRestaurant(Kaskus kaskus){
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		Google restaurant = googleController.getAllRestaurant(kaskus.getBody().toString());

		for(int i=0; i<5; i++){
			if(restaurant.getResults().size() == i){
				break;
			}else{
				GoogleDetail restaurantDetail = googleController.getDetailData(restaurant.getResults().get(i).getPlace_id());
				List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
				KaskusSendGenericButton kaskusSendGenericButton1 = new KaskusSendGenericButton(restaurantDetail.getResult().getWebsite(), "View", "recipient");
				kaskusSendGenericButtons.add(kaskusSendGenericButton1);
				KaskusSendGenericButton kaskusSendGenericButton2 = new KaskusSendGenericButton(restaurant.getResults().get(i).getName(), "View Review", "recipient");
				kaskusSendGenericButtons.add(kaskusSendGenericButton2);
				
				KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, googleController.getPhoto(restaurant.getResults().get(i).getPhotos().get(0).getPhoto_reference()),
						restaurant.getResults().get(i).getName(), restaurantDetail.getResult().getFormatted_address() + "\n" + restaurantDetail.getResult().getFormatted_phone_number());
				kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
				map.put(restaurant.getResults().get(i).getName(), restaurant.getResults().get(i).getPlace_id());
			}
		}
		
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+email,
				"Bot Hotel and Restaurant", "Insert your response");
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(147491, kaskusSendGenericSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
	}
	
	public void sendReviewRestaurant(Kaskus kaskus){
		
		List<KaskusSendGenericInteractive> kaskusSendGenericInteractives = new ArrayList<>();
		List<KaskusSendGenericSendList> kaskusSendGenericSendLists = new ArrayList<>();
		
		GoogleDetail restaurantDetail = googleController.getDetailData(map.get(kaskus.getBody()));

		for(int i=0; i<5; i++){
			List<KaskusSendGenericButton> kaskusSendGenericButtons = new ArrayList<>();
			KaskusSendGenericButton kaskusSendGenericButton = new KaskusSendGenericButton("Menu", "Menu", "recipient");
			kaskusSendGenericButtons.add(kaskusSendGenericButton);
			KaskusSendGenericInteractive kaskusSendGenericInteractive = new KaskusSendGenericInteractive(kaskusSendGenericButtons, restaurantDetail.getResult().getReviews().get(i).getProfile_photo_url(), 
					restaurantDetail.getResult().getReviews().get(i).getAuthor_name(), restaurantDetail.getResult().getReviews().get(i).getText());
			kaskusSendGenericInteractives.add(kaskusSendGenericInteractive);
		}
		
		KaskusSendGenericBody kaskusSendGenericBody = new KaskusSendGenericBody(kaskusSendGenericInteractives);
		KaskusSendGenericSendList kaskusSendGenericSendList = new KaskusSendGenericSendList(kaskusSendGenericBody, kaskus.getFromPlain()+email,
				"Bot Hotel and Restaurant", "Insert your response");
		kaskusSendGenericSendLists.add(kaskusSendGenericSendList);
		
		KaskusSendGeneric kaskusSendGeneric = new KaskusSendGeneric(147491, kaskusSendGenericSendLists);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,"Basic VGVzdEJvdDoxMjM0NTY3OA==");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<KaskusSendGeneric> entity1 = new HttpEntity<>(kaskusSendGeneric,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(kaskusKey, entity1, String.class);
		
	}
}
