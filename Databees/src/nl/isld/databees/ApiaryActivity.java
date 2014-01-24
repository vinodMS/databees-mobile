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

import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ApiaryActivity extends FragmentActivity
	implements OnMapLongClickListener, TextWatcher {
	
	public static final int			REQUEST_NEW_APIARY		= 100;
	public static final int			REQUEST_EDIT_APIARY		= 101;
	public static final String		REQUEST_EXTRA_APIARY_ID	= "REQUEST_EXTRA_APIARY_ID";
	public static final String		RESULT_EXTRA_APIARY_ID	= "RESULT_EXTRA_APIARY_ID";

	private Apiary					apiary;
	private EditText				apiaryName;
	private EditText				apiaryNotes;
	private SupportMapFragment		mapFragment;
	private GoogleMap				map;
	
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the NewApiaryActivity activity.
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apiary);
		
		initActionbar();
		initAttributes();
		initListeners();
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the options menu for the NewApiaryActivity activity.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_apiary_actions, menu);
	    return true;
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Called when an item in the options menu is selected.
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(checkRequired()) {
			switch(item.getItemId()) {
			case R.id.action_save:	finish();
									return true;
			}
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		super.finish();
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		intent.putExtra("REQUEST_CODE", requestCode);
		super.startActivityForResult(intent, requestCode);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {	
		case HiveActivity.REQUEST_NEW_HIVE:
			if(resultCode == RESULT_OK) {
				Hive hive = LocalStore.findHiveById(
						data.getStringExtra(HiveActivity.RESULT_EXTRA_HIVE_ID));
				apiary.addHive(hive);
				finish();
			}
		default:
			// Something went wrong obviously
			super.finish();
		}
	}

	/*
	 * Overridden method of interface OnMapLongClickListener.
	 * Called when a long click (tap) is performed on the map.
	 * It retrieves the LatLng position of the long click (tap)
	 * and places a marker on the map.
	 * @see com.google.android.gms.maps.GoogleMap.OnMapLongClickListener#onMapLongClick(com.google.android.gms.maps.model.LatLng)
	 */
	@Override
	public void onMapLongClick(LatLng point) {
		apiary.setLocation(point);
		drawMarker(point);
		Toast.makeText(getApplicationContext(),
				point.latitude + ", " + point.longitude,
				Toast.LENGTH_SHORT).show();
	}
	
	/*
	 * Overridden method of interface TextWatcher.
	 * Called after each text change of the linked EditTexts.
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable s) {

		if(getCurrentFocus().equals(apiaryName)) {
			apiary.setName(apiaryName.getText().toString().trim());
		}
		else if(getCurrentFocus().equals(apiaryNotes)) {
			apiary.setNotes(apiaryNotes.getText().toString().trim());
		}
	}
	
	/*
	 * Overridden method of interface TextWatcher.
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// Nothing to be done
	}

	/*
	 * Overridden method of interface TextWatcher.
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// Nothing to be done
	}
	
	public void onButtonLocateMeClicked(View view) {
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		
		if(status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	        dialog.show();
		} else {
			// Getting the best current location and placing a marker
			// on the map
	        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	        Criteria criteria = new Criteria();
	        String provider = locationManager.getBestProvider(criteria, true);
	        Location location = locationManager.getLastKnownLocation(provider);
	        
	        if(location != null) {
	        	LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
	        	apiary.setLocation(point);
		        drawMarker(point);
	        } else {
	        	Toast.makeText(this, "Unable to locate device", Toast.LENGTH_SHORT)
	        		.show();
	        }
		}
	}
	
	/*
	 * Called when the "Add Hives" button is clicked.
	 * If the apiary location is already set, it starts
	 * a new activity for the user to create a new hive.
	 * @param	view	the button view that triggered
	 * 					the method
	 */
	public void onButtonAddHivesClicked(View view) {
		if(checkRequired()) {
			Intent intent =
					new Intent(this, HiveActivity.class)
					.putExtra(HiveActivity.REQUEST_EXTRA_LOCATION, apiary.getLocation());
			startActivityForResult(intent, HiveActivity.REQUEST_NEW_HIVE);
		}
	}
	
	/*
	 * Saves the apiary to the store and finishes the activity.
	 * Note that is should always be called after a call to
	 * checkRequired() and only if the latter returns true.
	 */
	@Override
	public void finish() {
		
		switch(getIntent().getIntExtra("REQUEST_CODE", 0)) {
		
		case REQUEST_NEW_APIARY:
			LocalStore.APIARY_LIST.add(apiary);
			
		case REQUEST_EDIT_APIARY:
			// TODO
			break;
		}
		
		Intent intent = new Intent();
		intent.putExtra(RESULT_EXTRA_APIARY_ID, apiary.getId());
		setResult(RESULT_OK, intent);
		
		super.finish();
	}
	
	/*
	 * Initializes the activity's action bar.
	 */
	private void initActionbar() {
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setCustomView(R.layout.actionbar_new_item);
	}
	
	/*
	 * Sets the values of all attributes.
	 */
	private void initAttributes() {
		apiaryName = (EditText) getActionBar().getCustomView().findViewById(R.id.edit_text);
		apiaryNotes = (EditText) findViewById(R.id.notes_edit_text);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_fragment));
		map = mapFragment.getMap();
		
		switch(getIntent().getIntExtra("REQUEST_CODE", 0)) {
		
		case REQUEST_NEW_APIARY:
			apiary = new Apiary();
			break;
			
		case REQUEST_EDIT_APIARY:
			apiary = LocalStore.findApiaryById(
					getIntent().getStringExtra(REQUEST_EXTRA_APIARY_ID));
			if(apiary != null) {
				apiaryName.setText(apiary.getName());
				apiaryNotes.setText(apiary.getNotes());
				drawMarker(apiary.getLocation());
			}
			break;
		}
	}
	
	/*
	 * Sets up the listeners.
	 */
	private void initListeners() {
		map.setOnMapLongClickListener(this);
		apiaryName.addTextChangedListener(this);
		apiaryNotes.addTextChangedListener(this);
	}
	
	private void drawMarker(LatLng point) {
		map.clear();
		map.addMarker(new MarkerOptions().position(point));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 12));
	}
	
	/*
	 * Checks if the required information about the
	 * hive is given by the user. It also displays
	 * a toast prompt message if something is missing.
	 * @returns		true if all requirements are met,
	 * 				otherwise return false
	 */
	private boolean checkRequired() {
		
		if(apiary.getName().equals(new String())) {
			Toast.makeText(getApplicationContext(), 
					getResources().getString(R.string.toast_prompt_apiary_name),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(apiary.getLocation() == null) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.toast_prompt_apiary_location),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}

}
