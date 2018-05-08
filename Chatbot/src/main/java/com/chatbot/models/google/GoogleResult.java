package com.chatbot.models.google;

import java.util.List;
import java.util.ArrayList;

public class GoogleResult {
	private GoogleGeometry geometry;
	private String icon;
	private String id;
	private String name;
	private GoogleOpeningHours opening_hours;
	private List<GooglePhoto> photos;
	private String place_id;
	private String scope;
	private List<GoogleAltId> alt_ids;
	private String reference;
	private ArrayList<String> types = new ArrayList<String>();
	private String vicinity;
	
	public GoogleGeometry getGeometry() {
		return geometry;
	}
	
	public void setGeometry(GoogleGeometry geometry) {
		this.geometry = geometry;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public GoogleOpeningHours getOpening_hours() {
		return opening_hours;
	}
	
	public void setOpening_hours(GoogleOpeningHours opening_hours) {
		this.opening_hours = opening_hours;
	}
	
	public List<GooglePhoto> getPhotos() {
		return photos;
	}
	
	public void setPhotos(List<GooglePhoto> photos) {
		this.photos = photos;
	}
	
	public String getPlace_id() {
		return place_id;
	}
	
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public List<GoogleAltId> getAlt_ids() {
		return alt_ids;
	}
	
	public void setAlt_ids(List<GoogleAltId> alt_ids) {
		this.alt_ids = alt_ids;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public ArrayList<String> getTypes() {
		return types;
	}
	
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	
	public String getVicinity() {
		return vicinity;
	}
	
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	@Override
	public String toString() {
		return "GoogleResult [geometry=" + geometry + ", icon=" + icon + ", id=" + id + ", name=" + name
				+ ", opening_hours=" + opening_hours + ", photos=" + photos + ", place_id=" + place_id + ", scope="
				+ scope + ", alt_ids=" + alt_ids + ", reference=" + reference + ", types=" + types + ", vicinity="
				+ vicinity + "]";
	}
	
}
