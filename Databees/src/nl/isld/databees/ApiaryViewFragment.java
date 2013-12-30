package nl.isld.databees;

import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ApiaryViewFragment extends Fragment
	implements JsonParserCallback {
	
	public static final String	BACKSTACK_LABEL = "APIARY_VIEW_FRAGMENT";
	
	/*
	 * Overridden method of class Fragment.
	 * It returns the view to be used for the fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_apiary_view, container, false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		LatLng location = getArguments()
				.getParcelable(ApiaryListFragment.KEY_APIARY_LOCATION);
		OpenWeather.getWeather(location, this);
	}
	
	/*
	 * Overridden method of interface JsonParserCallback.
	 * Called once the JSON file retrieved from Open Weather
	 * is parsed.
	 */
	@Override
	public void onParsed(JSONObject json) {
		ApiaryInfoFragment apiaryInfoFragment =
				(ApiaryInfoFragment)
		this.getActivity().getSupportFragmentManager().findFragmentById(R.id.apiary_info_fragment);
		
		if (!json.equals(new JSONObject())) {
			WeatherData data = OpenWeather.dataFromJson(json);
			apiaryInfoFragment.setWeatherData(data);
		} else {
			// fragment.failWeatherData()
		}
	}

}
