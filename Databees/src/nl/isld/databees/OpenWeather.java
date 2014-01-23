package nl.isld.databees;

import com.google.android.gms.maps.model.LatLng;

public class OpenWeather {
	
	public static final String API_REQUEST_WEATHER =
			"http://api.openweathermap.org/data/2.5/weather?lat=$LAT&lon=$LNG";
	public static final String API_REQUEST_FORECAST =
			"http://api.openweathermap.org/data/2.5/forecast?lat=$LAT&lon=$LNG";
	public static final String API_REQUEST_DAILY_FORECAST =
			"http://api.openweathermap.org/data/2.5/forecast/daily?lat=$LAT&lon=$LNG&cnt=$CNT";
	
	public static final String RESPONSE_LABEL_COORD			= "coord";
	public static final String RESPONSE_LABEL_COORD_LAT		= "lat";
	public static final String RESPONSE_LABEL_COORD_LNG		= "lon";
	public static final String RESPONSE_LABEL_WEATHER		= "weather";
	public static final String RESPONSE_LABEL_WEATHER_ID	= "id";
	public static final String RESPONSE_LABEL_WEATHER_MAIN	= "main";
	public static final String RESPONSE_LABEL_MAIN			= "main";
	public static final String RESPONSE_LABEL_MAIN_TEMP		= "temp";
	
	public static void getWeather(LatLng location, ResponseRecipient recipient) {
		new AsyncJsonMessage().viaHttpRequest(API_REQUEST_WEATHER
				.replace("$LAT", String.valueOf(location.latitude))
				.replace("$LNG", String.valueOf(location.longitude)), recipient,
				false, AsyncJsonMessage.HttpMethod.POST);
	}
	
	public static void getForecast(LatLng location, ResponseRecipient recipient) {
		
	}
	
	public static void getDailyForecast(LatLng location, ResponseRecipient recipient) {
		
	}

}
