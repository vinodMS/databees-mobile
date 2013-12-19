package nl.isld.databees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewApiaryActivity extends FragmentActivity
	implements OnMapLongClickListener, TextWatcher {
	
	public static final int			RESULT_SAVE	=	1;
	public static final String		INTENT_EXTRA_LAT_LNG = "INTENT_EXTRA_LAT_LNG";

	private Apiary					newApiary;
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
		setContentView(R.layout.activity_new_apiary);
		
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
	    getMenuInflater().inflate(R.menu.activity_new_apiary_actions, menu);
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
			case R.id.action_save:
				AppCommon.APIARY_LOCAL_STORE.add(newApiary);
				setResult(RESULT_SAVE, new Intent());
				finish();
			}
		}
		return false;
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
		newApiary.setLocation(point);
		
		map.clear();
		map.addMarker(new MarkerOptions().position(point));
		map.animateCamera(CameraUpdateFactory.newLatLng(point));
		
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
			newApiary.setName(apiaryName.getText().toString().trim());
		}
		else if(getCurrentFocus().equals(apiaryNotes)) {
			newApiary.setNotes(apiaryNotes.getText().toString().trim());
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
					new Intent(this, NewHiveActivity.class)
					.putExtra(INTENT_EXTRA_LAT_LNG, newApiary.getLocation());
			startActivity(intent);
			finish();
		}
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
		newApiary = new Apiary();
		apiaryName = (EditText) getActionBar().getCustomView().findViewById(R.id.edit_text);
		apiaryNotes = (EditText) findViewById(R.id.notes_edit_text);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_fragment));
		map = mapFragment.getMap();
	}
	
	/*
	 * Sets up the listeners.
	 */
	private void initListeners() {
		map.setOnMapLongClickListener(this);
		apiaryName.addTextChangedListener(this);
		apiaryNotes.addTextChangedListener(this);
	}
	
	/*
	 * Checks if the required information about the
	 * hive is given by the user. It also displays
	 * a toast prompt message if something is missing.
	 * @returns		true if all requirements are met,
	 * 				otherwise return false
	 */
	private boolean checkRequired() {
		
		if(newApiary.getName().equals(new String())) {
			Toast.makeText(getApplicationContext(), 
					getResources().getString(R.string.toast_prompt_apiary_name),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(newApiary.getLocation() == null) {
			Toast.makeText(getApplicationContext(), 
					getResources().getString(R.string.toast_prompt_apiary_location),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}

}
