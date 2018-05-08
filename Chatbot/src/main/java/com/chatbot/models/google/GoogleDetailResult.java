package com.chatbot.models.google;

import java.util.List;
import java.util.ArrayList;

public class GoogleDetailResult {
	private List<GoogleDetailAddress> address_components;
	private String adr_address;
	private String formatted_address;
	private String formatted_phone_number;
	private GoogleDetailGeometry geometry;
	private String icon;
	private String id;
	private String international_phone_number;
	private String name;
	private String place_id;
	private String rating;
	private String reference;
	private List<GoogleDetailReview> reviews;
	private String scope;
	private ArrayList<String> types = new ArrayList<String>();
	private String url;
	private long utc_offset;
	private String vicinity;
	private String website;
	
	public List<GoogleDetailAddress> getAddress_components() {
		return address_components;
	}
	
	public void setAddress_components(List<GoogleDetailAddress> address_components) {
		this.address_components = address_components;
	}
	
	public String getAdr_address() {
		return adr_address;
	}
	
	public void setAdr_address(String adr_address) {
		this.adr_address = adr_address;
	}
	
	public String getFormatted_address() {
		return formatted_address;
	}
	
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	
	public String getFormatted_phone_number() {
		return formatted_phone_number;
	}
	
	public void setFormatted_phone_number(String formatted_phone_number) {
		this.formatted_phone_number = formatted_phone_number;
	}
	
	public GoogleDetailGeometry getGeometry() {
		return geometry;
	}
	
	public void setGeometry(GoogleDetailGeometry geometry) {
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
	
	public String getInternational_phone_number() {
		return international_phone_number;
	}
	
	public void setInternational_phone_number(String international_phone_number) {
		this.international_phone_number = international_phone_number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPlace_id() {
		return place_id;
	}
	
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public List<GoogleDetailReview> getReviews() {
		return reviews;
	}
	
	public void setReviews(List<GoogleDetailReview> reviews) {
		this.reviews = reviews;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public ArrayList<String> getTypes() {
		return types;
	}
	
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getUtc_offset() {
		return utc_offset;
	}
	
	public void setUtc_offset(long utc_offset) {
		this.utc_offset = utc_offset;
	}
	
	public String getVicinity() {
		return vicinity;
	}
	
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "GoogleDetailResult [address_components=" + address_components + ", adr_address=" + adr_address
				+ ", formatted_address=" + formatted_address + ", formatted_phone_number=" + formatted_phone_number
				+ ", geometry=" + geometry + ", icon=" + icon + ", id=" + id + ", international_phone_number="
				+ international_phone_number + ", name=" + name + ", place_id=" + place_id + ", rating=" + rating
				+ ", reference=" + reference + ", reviews=" + reviews + ", scope=" + scope + ", types=" + types
				+ ", url=" + url + ", utc_offset=" + utc_offset + ", vicinity=" + vicinity + ", website=" + website
				+ "]";
	}
	
}
