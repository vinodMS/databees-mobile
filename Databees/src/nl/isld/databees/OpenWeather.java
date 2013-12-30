package nl.isld.databees;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class OpenWeather {
	
	public static final String API_REQUEST_WEATHER =
			"http://api.openweathermap.org/data/2.5/weather?lat=$LAT&lon=$LNG";
	public static final String API_REQUEST_FORECAST =
			"http://api.openweathermap.org/data/2.5/forecast?lat=$LAT&lon=$LNG";
	public static final String API_REQUEST_DAILY_FORECAST =
			"http://api.openweathermap.org/data/2.5/forecast/daily?lat=$LAT&lon=$LNG&cnt=$CNT";
	
	private static final String RESPONSE_LABEL_COORD		= "coord";
	private static final String RESPONSE_LABEL_COORD_LAT	= "lat";
	private static final String RESPONSE_LABEL_COORD_LNG	= "lon";
	private static final String RESPONSE_LABEL_WEATHER		= "weather";
	private static final String RESPONSE_LABEL_WEATHER_ID	= "id";
	private static final String RESPONSE_LABEL_WEATHER_MAIN	= "main";
	private static final String RESPONSE_LABEL_MAIN			= "main";
	private static final String RESPONSE_LABEL_MAIN_TEMP	= "temp";
	
	public static void getWeather(LatLng location, JsonParserCallback callback) {
		new JsonParser().parse(API_REQUEST_WEATHER
				.replace("$LAT", String.valueOf(location.latitude))
				.replace("$LNG", String.valueOf(location.longitude)), callback);
	}
	
	public static void getForecast(LatLng location, JsonParserCallback callback) {
		
	}
	
	public static void getDailyForecast(LatLng location, JsonParserCallback callback) {
		
	}
	
	public static WeatherData dataFromJson(JSONObject json) {
		WeatherData weather = new WeatherData();
		
		try {
			LatLng location = new LatLng(
					json.getJSONObject(RESPONSE_LABEL_COORD).getDouble(RESPONSE_LABEL_COORD_LAT),
					json.getJSONObject(RESPONSE_LABEL_COORD).getDouble(RESPONSE_LABEL_COORD_LNG));
			int conditionCode =
					json.getJSONArray(RESPONSE_LABEL_WEATHER)
					.getJSONObject(0).getInt(RESPONSE_LABEL_WEATHER_ID);
			String condition =
					json.getJSONArray(RESPONSE_LABEL_WEATHER)
					.getJSONObject(0).getString(RESPONSE_LABEL_WEATHER_MAIN);
			Double temperature =
					json.getJSONObject(RESPONSE_LABEL_MAIN).getDouble(RESPONSE_LABEL_MAIN_TEMP);
			
			weather.setLocation(location);
			weather.setConditionCode(conditionCode);
			weather.setCondition(condition);
			weather.setTemperature(temperature);
			
		} catch( JSONException e ) {
			e.printStackTrace();
		}
		
		return weather;
	}

}
