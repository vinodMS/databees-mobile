package nl.isld.databees;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class WeatherData {
	
	private LatLng		location;
	private int			conditionCode;
	private String		condition;
	private double		temperature;
	
	
	public LatLng getLocation() {
		return this.location;
	}
	
	public int getConditionCode() {
		return conditionCode;
	}
	
	public String getCondition() {
		return this.condition;
	}
	
	public double getTemperature() {
		return this.temperature;
	}
	
	public double getTemperatureCelcius() {
		return temperature - 273.15;
	}
	
	public void setLocation(LatLng location) {
		this.location = location;
	}
	
	public void setConditionCode(int conditionCode) {
		this.conditionCode = conditionCode;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public static WeatherData fromOpenWeatherJsonObject(JSONObject json) {
		WeatherData weather = new WeatherData();
		
		try {
			LatLng location = new LatLng(
					json.getJSONObject(OpenWeather.RESPONSE_LABEL_COORD)
						.getDouble(OpenWeather.RESPONSE_LABEL_COORD_LAT),
					json.getJSONObject(OpenWeather.RESPONSE_LABEL_COORD)
						.getDouble(OpenWeather.RESPONSE_LABEL_COORD_LNG));
			int conditionCode =
					json.getJSONArray(OpenWeather.RESPONSE_LABEL_WEATHER)
						.getJSONObject(0).getInt(OpenWeather.RESPONSE_LABEL_WEATHER_ID);
			String condition =
					json.getJSONArray(OpenWeather.RESPONSE_LABEL_WEATHER)
						.getJSONObject(0).getString(OpenWeather.RESPONSE_LABEL_WEATHER_MAIN);
			Double temperature =
					json.getJSONObject(OpenWeather.RESPONSE_LABEL_MAIN).getDouble(OpenWeather.RESPONSE_LABEL_MAIN_TEMP);
			
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
