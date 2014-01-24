/*
	Databees a beekeeping organizer app.
    Copyright (C) 2014 NBV (Nederlandse Bijenhouders Vereniging)
    http://www.bijenhouders.nl/

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
