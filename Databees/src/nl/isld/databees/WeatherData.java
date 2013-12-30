package nl.isld.databees;

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

}
