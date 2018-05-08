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
import com.chatbot.models.line.Line;
import com.chatbot.models.line.LineSend;
import com.chatbot.models.line.LineSendButton;
import com.chatbot.models.line.LineSendButtonMessages;
import com.chatbot.models.line.LineSendButtonAction;
import com.chatbot.models.line.LineSendButtonTemplate;
import com.chatbot.models.line.LineSendCarousel;
import com.chatbot.models.line.LineSendCarouselMessage;
import com.chatbot.models.line.LineSendCarouselAction;
import com.chatbot.models.line.LineSendCarouselColumn;
import com.chatbot.models.line.LineSendCarouselTemplate;
import com.chatbot.models.line.LineSendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class LineController {
	@Autowired
	public RestTemplate restTemplate;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	GoogleController googleController;
	
	ObjectMapper mapper = new ObjectMapper();
	private String title,text;
	String lineUrl = "https://api.line.me/v2/bot/message/push";
	String authorization = "Bearer yH86gbymWPfjZQhRfB8kZ23HqMsdn7qykltlis+8IWyI+RMqY2NFc0db0E68FIp2b1z/KTS6B2kgF5tLd3XvsGklbScAFX8Qgsfbi7Szn8uvAabTF5D6qvDc8iIUEWRCdkNmD/rtlCFQj9gKPBz1FgdB04t89/1O/w1cDnyilFU=";
	Map<String, String> map = new HashMap<String, String>();
	
	@RequestMapping(value= "/lineWebhook", method = RequestMethod.GET)
	public String getChallenge(HttpServletRequest request){
		String challenge = request.getParameter("hub.challenge");
		return challenge;
	}
	
	@RequestMapping(value= "/lineWebhook", method = RequestMethod.POST)
	public void getMessage(@RequestBody Line line){
		System.out.println(line);
		String idUsername = line.getEvents().get(0).getSource().getUserId();
		if(stateRepository.findByIdUsername(idUsername) == null){
			Event event = eventRepository.findOne(1);
			State states = new State(idUsername, event);
			stateRepository.save(states);
			sendMessageText(line);
			sendMessageCarousel(line);
		}else{
			Integer idState = stateRepository.findByIdUsername(idUsername).getIdEvent().getIdEvent();
			Integer id = stateRepository.findByIdUsername(idUsername).getIdState().intValue();
			if(idState == 1){
				if(line.getEvents().get(0).getMessage() == null){
					if(line.getEvents().get(0).getPostback().getData().equalsIgnoreCase("Hotel")){
						sendMessageTextNext(line);
						Event eventId = eventRepository.findOne(2);
						stateRepository.editEvent(eventId , id);
					}else if(line.getEvents().get(0).getPostback().getData().equalsIgnoreCase("Restaurant")){
						sendMessageTextNext(line);
						Event eventId = eventRepository.findOne(3);
						stateRepository.editEvent(eventId , id);
					}
				}else if(line.getEvents().get(0).getMessage() != null){
					sendMessageText(line);
					sendMessageCarousel(line);
				}
			}else if(idState == 2){
				if(line.getEvents().get(0).getMessage() != null){
					Google hotel = googleController.getAllHotel(line.getEvents().get(0).getMessage().getText());
					if(hotel.getResults().isEmpty()){
						sendMessageTextNext(line);
					}else{
						sendListHotel(line);
						sendMessageMenu(line);
						Event eventId = eventRepository.findOne(4);
						stateRepository.editEvent(eventId , id);
					}
				}
			}else if(idState == 3){
				if(line.getEvents().get(0).getMessage() != null){
					Google restaurant = googleController.getAllRestaurant(line.getEvents().get(0).getMessage().getText());
					if(restaurant.getResults().isEmpty()){
						sendMessageTextNext(line);
					}else{
						sendListRestaurant(line);
						sendMessageMenu(line);
						Event eventId = eventRepository.findOne(5);
						stateRepository.editEvent(eventId , id);
					}
				}
			}else if(idState == 4){
				if(map.get(line.getEvents().get(0).getPostback().getData()) != null){
					sendReviewHotel(line);
					Event eventId = eventRepository.findOne(6);
					stateRepository.editEvent(eventId , id);
				}else if(line.getEvents().get(0).getMessage() == null){
					if(line.getEvents().get(0).getPostback().getData().equalsIgnoreCase("Menu")){
						sendMessageText(line);
						sendMessageCarousel(line);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else{
						sendMessageMenu(line);
					}
				}else{
					sendMessageTextNext(line);
				}
			}else if(idState == 5){
				if(map.get(line.getEvents().get(0).getPostback().getData()) != null){
					sendReviewRestaurant(line);
					Event eventId = eventRepository.findOne(6);
					stateRepository.editEvent(eventId , id);
				}else if(line.getEvents().get(0).getMessage() == null){
					if(line.getEvents().get(0).getPostback().getData().equalsIgnoreCase("Menu")){
						sendMessageText(line);
						sendMessageCarousel(line);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else{
						sendMessageMenu(line);
					}
				}else{
					sendMessageTextNext(line);
				}
			}else if(idState == 6){
				if(line.getEvents().get(0).getMessage() == null){
					if(line.getEvents().get(0).getPostback().getData().equalsIgnoreCase("Menu")){
						sendMessageText(line);
						sendMessageCarousel(line);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else{
						sendMessageMenu(line);
					}
				}else{
					if(line.getEvents().get(0).getMessage().getText().equalsIgnoreCase("Menu")){
						sendMessageText(line);
						sendMessageCarousel(line);
						Event eventId = eventRepository.findOne(1);
						stateRepository.editEvent(eventId , id);
					}else{
						sendMessageMenu(line);
					}
				}
			}
		}	
	}
	
	public void sendMessageText(Line line){
		List<LineSendMessage> lineSendMessages = new ArrayList<>();
		
		LineSendMessage lineSendMessage = new LineSendMessage("text", "Hai!Selamat Datang,\n"
				+ "Saya EASY,disini kami bisa membantu anda untuk menemukan tempat penginapan"
				+ " serta restoran terdekat"
				+ " dengan memilih menu dibawah ini");
		lineSendMessages.add(lineSendMessage);
		LineSend lineSend = new LineSend(line.getEvents().get(0).getSource().getUserId(), lineSendMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSend> entity1 = new HttpEntity<>(lineSend,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void sendMessageTextNext(Line line){
		List<LineSendMessage> lineSendMessages = new ArrayList<>();
		LineSendMessage lineSendMessage = new LineSendMessage("text", "Silahkan masukkan kota yang ingin dicari");
		lineSendMessages.add(lineSendMessage);
		
		LineSend lineSend = new LineSend(line.getEvents().get(0).getSource().getUserId(), lineSendMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSend> entity1 = new HttpEntity<>(lineSend,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void sendMessageCarousel(Line line){
		List<LineSendCarouselAction> lineSendCarouselActions = new ArrayList<>();
		LineSendCarouselAction lineSendCarouselAction1 = new LineSendCarouselAction("postback", "Hotel", "Hotel", null);
		lineSendCarouselActions.add(lineSendCarouselAction1);
		LineSendCarouselAction lineSendCarouselAction2 = new LineSendCarouselAction("postback", "Restaurant", "Restaurant", null);
		lineSendCarouselActions.add(lineSendCarouselAction2);
		
		List<LineSendCarouselColumn> lineSendCarouselColumns = new ArrayList<>();
		LineSendCarouselColumn lineSendCarouselColumn1 = new LineSendCarouselColumn("https://www.barcelo.com/en-us/images/385-swimming-pool-4-hotel-barcelo-royal-hideaway-playacar-resort_tcm21-34268_w1600_h611_n.jpg",
				"EASY", "We are the right answer for everyone!", lineSendCarouselActions);
		lineSendCarouselColumns.add(lineSendCarouselColumn1);
		LineSendCarouselTemplate lineSendCarouselTemplate = new LineSendCarouselTemplate("carousel", lineSendCarouselColumns);
		
		List<LineSendCarouselMessage> lineSendCarouselMessages = new ArrayList<>();
		LineSendCarouselMessage lineSendCarouselMessage = new LineSendCarouselMessage("template", "This is a carousel template",lineSendCarouselTemplate);
		lineSendCarouselMessages.add(lineSendCarouselMessage);
		
		LineSendCarousel lineSendCarousel = new LineSendCarousel(line.getEvents().get(0).getSource().getUserId(), lineSendCarouselMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

		HttpEntity<LineSendCarousel> entity1 = new HttpEntity<>(lineSendCarousel,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void sendMessageMenu(Line line){
		List<LineSendCarouselAction> lineSendCarouselActions = new ArrayList<>();
		LineSendCarouselAction lineSendCarouselAction = new LineSendCarouselAction("postback", "Menu", "Menu", null);
		lineSendCarouselActions.add(lineSendCarouselAction);
		
		List<LineSendCarouselColumn> lineSendCarouselColumns = new ArrayList<>();
		LineSendCarouselColumn lineSendCarouselColumn1 = new LineSendCarouselColumn(null, null, "Pilih Menu untuk kembali", lineSendCarouselActions);
		lineSendCarouselColumns.add(lineSendCarouselColumn1);
		LineSendCarouselTemplate lineSendCarouselTemplate = new LineSendCarouselTemplate("carousel", lineSendCarouselColumns);
		
		List<LineSendCarouselMessage> lineSendCarouselMessages = new ArrayList<>();
		LineSendCarouselMessage lineSendCarouselMessage = new LineSendCarouselMessage("template", "This is a carousel template",lineSendCarouselTemplate);
		lineSendCarouselMessages.add(lineSendCarouselMessage);
		
		LineSendCarousel lineSendCarousel = new LineSendCarousel(line.getEvents().get(0).getSource().getUserId(), lineSendCarouselMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSendCarousel> entity1 = new HttpEntity<>(lineSendCarousel,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void sendListHotel(Line line){
		List<LineSendCarouselMessage> lineSendCarouselMessages = new ArrayList<>();
		List<LineSendCarouselColumn> lineSendCarouselColumns = new ArrayList<>();
		Google hotel = googleController.getAllHotel(line.getEvents().get(0).getMessage().getText());
		
		for(int i=0; i<5; i++){
			if(hotel.getResults().size() == i){
				break;
			}else{
				List<LineSendCarouselAction> lineSendCarouselActions = new ArrayList<>();
				GoogleDetail hotelDetail = googleController.getDetailData(hotel.getResults().get(i).getPlace_id());
				cekLimitedTitle(hotel,i);
				cekLimitedText(hotelDetail);
				
				if(hotelDetail.getResult().getWebsite() != null){
					LineSendCarouselAction lineSendCarouselAction1 = new LineSendCarouselAction("uri", "View", null, hotelDetail.getResult().getWebsite());
					lineSendCarouselActions.add(lineSendCarouselAction1);
				}else{
					LineSendCarouselAction lineSendCarouselAction1 = new LineSendCarouselAction("uri", "View", null, "https://www.google.com");
					lineSendCarouselActions.add(lineSendCarouselAction1);
				}
				
				LineSendCarouselAction lineSendCarouselAction2 = new LineSendCarouselAction("postback", "View Review", title, null);
				lineSendCarouselActions.add(lineSendCarouselAction2);
				
				LineSendCarouselColumn lineSendCarouselColumn1 = new LineSendCarouselColumn(googleController.getPhoto(hotel.getResults().get(i).getPhotos().get(0).getPhoto_reference()), title, 
						text + "\n" + hotelDetail.getResult().getFormatted_phone_number(), lineSendCarouselActions);
				lineSendCarouselColumns.add(lineSendCarouselColumn1);
				map.put(title, hotel.getResults().get(i).getPlace_id());
			}
		}	
			
		LineSendCarouselTemplate lineSendCarouselTemplate = new LineSendCarouselTemplate("carousel", lineSendCarouselColumns);
		LineSendCarouselMessage lineSendCarouselMessage = new LineSendCarouselMessage("template", "This is a carousel template",lineSendCarouselTemplate);
		lineSendCarouselMessages.add(lineSendCarouselMessage);
		LineSendCarousel lineSendCarousel = new LineSendCarousel(line.getEvents().get(0).getSource().getUserId(), lineSendCarouselMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSendCarousel> entity1 = new HttpEntity<>(lineSendCarousel,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void sendListRestaurant(Line line){
		List<LineSendCarouselMessage> lineSendCarouselMessages = new ArrayList<>();
		List<LineSendCarouselColumn> lineSendCarouselColumns = new ArrayList<>();
		Google restaurant = googleController.getAllRestaurant(line.getEvents().get(0).getMessage().getText());
		
		for(int i=0; i<5; i++){
			if(restaurant.getResults().size() == i){
				break;
			}else{
				List<LineSendCarouselAction> lineSendCarouselActions = new ArrayList<>();			
				GoogleDetail restaurantDetail = googleController.getDetailData(restaurant.getResults().get(i).getPlace_id());
				cekLimitedTitle(restaurant,i);
				cekLimitedText(restaurantDetail);
				
				if(restaurantDetail.getResult().getWebsite() != null){
					LineSendCarouselAction lineSendCarouselAction1 = new LineSendCarouselAction("uri", "View", null, restaurantDetail.getResult().getWebsite());
					lineSendCarouselActions.add(lineSendCarouselAction1);
				}else{
					LineSendCarouselAction lineSendCarouselAction1 = new LineSendCarouselAction("uri", "View", null, "https://www.google.com");
					lineSendCarouselActions.add(lineSendCarouselAction1);
				}
				
				LineSendCarouselAction lineSendCarouselAction2 = new LineSendCarouselAction("postback", "View Review", title, null);
				lineSendCarouselActions.add(lineSendCarouselAction2);
				
				LineSendCarouselColumn lineSendCarouselColumn1 = new LineSendCarouselColumn(googleController.getPhoto(restaurant.getResults().get(i).getPhotos().get(0).getPhoto_reference()), title, 
						text + "\n" + restaurantDetail.getResult().getFormatted_phone_number(), lineSendCarouselActions);
				lineSendCarouselColumns.add(lineSendCarouselColumn1);
				map.put(title, restaurant.getResults().get(i).getPlace_id());
			}
		}	
			
		LineSendCarouselTemplate lineSendCarouselTemplate = new LineSendCarouselTemplate("carousel", lineSendCarouselColumns);
		LineSendCarouselMessage lineSendCarouselMessage = new LineSendCarouselMessage("template", "This is a carousel template",lineSendCarouselTemplate);
		lineSendCarouselMessages.add(lineSendCarouselMessage);
		LineSendCarousel lineSendCarousel = new LineSendCarousel(line.getEvents().get(0).getSource().getUserId(), lineSendCarouselMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSendCarousel> entity1 = new HttpEntity<>(lineSendCarousel,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void cekLimitedTitle(Google google, int i){
			if(google.getResults().get(i).getName().length() <= 40){
				title = google.getResults().get(i).getName();
			}else{
				title = google.getResults().get(i).getName().substring(0,40);
			}
	}
	
	public void cekLimitedText(GoogleDetail googleDetail){
		for(int i=0; i<5; i++){
			if(googleDetail.getResult().getFormatted_address().length() <=45){
				text = googleDetail.getResult().getFormatted_address(); 
			}else{
				text = googleDetail.getResult().getFormatted_address().substring(0,45);
			}
		}
	}
	
	public void cekLimitedTextDetail(GoogleDetail googleDetail){
		for(int i=0; i<5; i++){
			if(googleDetail.getResult().getReviews().get(i).getText().length() <= 60){
				text = googleDetail.getResult().getReviews().get(i).getText(); 
			}else{
				text = googleDetail.getResult().getReviews().get(i).getText().substring(0,60);
			}
		}
	}
	
	public void sendReviewHotel(Line line){
		List<LineSendCarouselMessage> lineSendCarouselMessages = new ArrayList<>();
		List<LineSendCarouselColumn> lineSendCarouselColumns = new ArrayList<>();
		
		GoogleDetail hotelDetail = googleController.getDetailData(map.get(line.getEvents().get(0).getPostback().getData()));
		
		for(int i=0; i<5; i++){
			if(hotelDetail.getResult().getReviews().size() == i){
				break;
			}else{
				List<LineSendCarouselAction> lineSendCarouselActions = new ArrayList<>();
				cekLimitedTextDetail(hotelDetail);
				
				LineSendCarouselAction lineSendCarouselAction2 = new LineSendCarouselAction("postback", "Menu", "Menu", null);
				lineSendCarouselActions.add(lineSendCarouselAction2);
				
				LineSendCarouselColumn lineSendCarouselColumn1 = new LineSendCarouselColumn(googleController.getPhoto(hotelDetail.getResult().getReviews().get(i).getProfile_photo_url()), hotelDetail.getResult().getReviews().get(i).getAuthor_name(), 
						text, lineSendCarouselActions);
				lineSendCarouselColumns.add(lineSendCarouselColumn1);
			}
		}	
			
		LineSendCarouselTemplate lineSendCarouselTemplate = new LineSendCarouselTemplate("carousel", lineSendCarouselColumns);
		LineSendCarouselMessage lineSendCarouselMessage = new LineSendCarouselMessage("template", "This is a carousel template",lineSendCarouselTemplate);
		lineSendCarouselMessages.add(lineSendCarouselMessage);
		LineSendCarousel lineSendCarousel = new LineSendCarousel(line.getEvents().get(0).getSource().getUserId(), lineSendCarouselMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSendCarousel> entity1 = new HttpEntity<>(lineSendCarousel,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
	public void sendReviewRestaurant(Line line){
		List<LineSendCarouselMessage> lineSendCarouselMessages = new ArrayList<>();
		List<LineSendCarouselColumn> lineSendCarouselColumns = new ArrayList<>();
		
		GoogleDetail restaurantDetail = googleController.getDetailData(map.get(line.getEvents().get(0).getPostback().getData()));
		
		for(int i=0; i<5; i++){
			if(restaurantDetail.getResult().getReviews().size() == i){
				break;
			}else{
				List<LineSendCarouselAction> lineSendCarouselActions = new ArrayList<>();
				cekLimitedTextDetail(restaurantDetail);
				
				LineSendCarouselAction lineSendCarouselAction2 = new LineSendCarouselAction("postback", "Menu", "Menu", null);
				lineSendCarouselActions.add(lineSendCarouselAction2);
				
				LineSendCarouselColumn lineSendCarouselColumn1 = new LineSendCarouselColumn(googleController.getPhoto(restaurantDetail.getResult().getReviews().get(i).getProfile_photo_url()), restaurantDetail.getResult().getReviews().get(i).getAuthor_name(), 
						text, lineSendCarouselActions);
				lineSendCarouselColumns.add(lineSendCarouselColumn1);
			}
		}	
			
		LineSendCarouselTemplate lineSendCarouselTemplate = new LineSendCarouselTemplate("carousel", lineSendCarouselColumns);
		LineSendCarouselMessage lineSendCarouselMessage = new LineSendCarouselMessage("template", "This is a carousel template",lineSendCarouselTemplate);
		lineSendCarouselMessages.add(lineSendCarouselMessage);
		LineSendCarousel lineSendCarousel = new LineSendCarousel(line.getEvents().get(0).getSource().getUserId(), lineSendCarouselMessages);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authorization);
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<LineSendCarousel> entity1 = new HttpEntity<>(lineSendCarousel,headers);
		ResponseEntity<String> result = restTemplate.postForEntity(lineUrl, entity1, String.class);
	}
	
}
