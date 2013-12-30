package nl.isld.databees;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ApiaryInfoFragment extends Fragment {
	
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
	}
	
	public void setWeatherData(WeatherData data) {
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
		weatherTemperature.setText((int) data.getTemperatureCelcius() + "�C");
		
	}
	
}
