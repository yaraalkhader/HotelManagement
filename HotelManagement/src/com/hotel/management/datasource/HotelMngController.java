package com.hotel.management.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hotel.management.model.Hotel;

public class HotelMngController {

	private static final String JSON_API = "https://offersvc.expedia.com/offers/v2/getOffers?scenario=deal-finder&page=foo&uid=foo&productType=Hotel";
	private static final String PARAMETER_DESTINATION_NAME = "hotelDestination";
	private static final String PARAMETER_MIN_TRIP_START_DATE = "minTripStartDate";
	private static final String PARAMETER_MAX_TRIP_START_DATE = "maxTripStartDate";
	private static final String PARAMETER_LENGTH_OF_STAY = "lengthOfStay";
	private static final String PARAMETER_MIN_STAR_RATING = "minStarRating";
	private static final String PARAMETER_MAX_STAR_RATING = "maxStarRating";
	private static final String PARAMETER_MIN_TOTAL_RATE = "minTotalRate";
	private static final String PARAMETER_MAX_TOTAL_RATE = "maxTotalRate";
	private static final String PARAMETER_MIN_GUEST_RATING = "minGuestRating";
	private static final String PARAMETER_MAX_GUEST_RATING = "maxGuestRating";
	
	private static final String URL_PARAM_START = "&";
	private static final String URL_PARAM_EQUAL = "=";
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<Hotel> searchForHotels(String destinationName,
			Date minTripStartDate, Date maxTripStartDate, String lengthOfStay,
			String minStarRating, String maxStarRating, String minTotalRate,
			String maxTotalRate, String minGuestRating, String maxGuestRating) {
		
		String urlStr = JSON_API;
		if (validateNotEmpty(destinationName)){
			urlStr = urlStr + getParameter(PARAMETER_DESTINATION_NAME, normalize(destinationName.trim()));
		}
				
		if (validateNotEmpty(minTripStartDate)){
			urlStr = urlStr + getParameter(PARAMETER_MIN_TRIP_START_DATE, normalize(minTripStartDate));
		}
		
		if (validateNotEmpty(maxTripStartDate)){
			urlStr = urlStr + getParameter(PARAMETER_MAX_TRIP_START_DATE, normalize(maxTripStartDate));
		}
		
		
		if (validateNotEmpty(lengthOfStay)){
			urlStr = urlStr + getParameter(PARAMETER_LENGTH_OF_STAY, normalize(lengthOfStay.trim()));
		}		
		
		if (validateNotEmpty(minStarRating)){
			urlStr = urlStr + getParameter(PARAMETER_MIN_STAR_RATING, normalize(minStarRating.trim()));
		}
				
		if (validateNotEmpty(maxStarRating)){
			urlStr = urlStr + getParameter(PARAMETER_MAX_STAR_RATING, normalize(maxStarRating.trim()));
		}
		
		if (validateNotEmpty(minTotalRate)){
			urlStr = urlStr + getParameter(PARAMETER_MIN_TOTAL_RATE, normalize(minTotalRate.trim()));
		}
		
		if (validateNotEmpty(maxTotalRate)){
			urlStr = urlStr + getParameter(PARAMETER_MAX_TOTAL_RATE, normalize(maxTotalRate.trim()));
		}
		
		if (validateNotEmpty(minGuestRating)){
			urlStr = urlStr + getParameter(PARAMETER_MIN_GUEST_RATING, normalize(minGuestRating.trim()));
		}
		
		if (validateNotEmpty(maxGuestRating)){
			urlStr = urlStr + getParameter(PARAMETER_MAX_GUEST_RATING, normalize(maxGuestRating.trim()));
		}
		return jsonHandler(urlStr);
	}
	
	private boolean validateNotEmpty(String value){
		return value == null || value.isEmpty() ? false : true;
	}
	
	private boolean validateNotEmpty(Date value){
		return value == null ? false : true;
	}
	
	private String normalize(String value) {
		return value.replace(" ", "%20");
	}
	
	private String normalize(Date value) {
		return dateFormatter.format(value);
	}
	
	private String getParameter(String name, String value) {
		return URL_PARAM_START + name + URL_PARAM_EQUAL + value;
	}

	public static List<Hotel> jsonHandler(String urlStr) {
		URL url;
		List<Hotel> hotelList = new ArrayList<Hotel>();
		try {
			url = new URL(urlStr);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			JsonParser jp = new JsonParser(); 
			JsonElement root = jp.parse(new InputStreamReader((InputStream) request
					.getContent())); // Convert the input stream to a json element
			JsonObject rootobj = root.getAsJsonObject();
			hotelList.add(new Hotel(rootobj.toString()));
			System.out.println(rootobj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// String zipcode = rootobj.get("zip_code").getAsString();
		return hotelList;
	}

	public void parseJson(String rootobj) {

	}

	public void getHotel() {

	}

	public static void main(String[] args) {
		
			HotelMngController.jsonHandler(null);
		
	}

}
