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

import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ApiaryInfoFragment extends Fragment
	implements ResponseRecipient {
	
	public static final String 	ARG_LOCATION = "ARG_LOCATION";
	
	private ImageView	weatherIcon;
	private TextView	weatherCondition;
	private TextView	weatherTemperature;

	/*
	 * Overridden method of class Fragment.
	 * It returns the view to be used for the fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_apiary_info, container, false);
	}
	
	/*
	 * Overridden method of class Fragment.
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		weatherIcon = (ImageView) getView().findViewById(R.id.weather_icon);
		weatherCondition = (TextView) getView().findViewById(R.id.weather_condition);
		weatherTemperature = (TextView) getView().findViewById(R.id.weather_temperature);
		
		LatLng location = (LatLng) getArguments().getParcelable(ARG_LOCATION);
		OpenWeather.getWeather(location, this);
	}
	
	/*
	 * Overridden method of interface JsonParserCallback.
	 * Called once the JSON file retrieved from Open Weather
	 * is parsed.
	 */
	@Override
	public void onResponseReceived(JSONObject json) {
		
		if (!json.equals(new JSONObject())) {
			WeatherData data = WeatherData.fromOpenWeatherJsonObject(json);
			setWeatherData(data);
		} else {
			// failWeatherData()
		}
	}
	
	/*
	 * Private method called when the JSON response from
	 * OpenWeather has been successfully retrieved and
	 * parsed. This method takes care of refreshing the UI.
	 */
	private void setWeatherData(WeatherData data) {
		
		if(getView() == null) {
			return;
		}
		
		switch(data.getConditionCode()) {
		case 500: case 501: case 502:
		case 503: case 504: case 511:
		case 520: case 521: case 522:
		case 531:
			weatherIcon.setImageDrawable(
					getResources().getDrawable(R.drawable.weather_condition_rain));
			break;
		case 800: case 801: case 802:
		case 803: case 804:
			weatherIcon.setImageDrawable(
					getResources().getDrawable(R.drawable.weather_condition_clouds));
			break;
		default:
			weatherIcon.setImageDrawable(
					getResources().getDrawable(R.drawable.weather_condition_clear));
		}
		
		weatherCondition.setText(data.getCondition());
		weatherTemperature.setText((int) data.getTemperatureCelcius() + "°C");
		
		getView().findViewById(R.id.progress_spin).setVisibility(View.INVISIBLE);
		getView().findViewById(R.id.weather_info_container).setVisibility(View.VISIBLE);
	}
	
}
