package nl.isld.databees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NewHiveActivity extends FragmentActivity
	implements TextWatcher {
	
	public static final int			RESULT_SAVE	=	1;
	
	private Intent					intent;
	private	NewHiveFrontFragment	front;
	private NewHiveBackFragment		back;
	private boolean					showingBack;
	private Hive					newHive;
	private EditText				hiveName;
	private SupportMapFragment		mapFragment;
	private GoogleMap				map;
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the NewHiveActivity activity.
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_hive);
		
		initActionbar();
		initAttributes();
		initMap();
		initListeners();
		initFrame();
		
		Log.d("Debug", "Waiting for events in NewHiveActivity");
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the options menu for the NewHiveActivity activity.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_new_hive_actions, menu);
	    return true;
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Called when an item in the options menu is selected.
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_save:
			AppCommon.HIVE_LOCAL_STORE.add(newHive);
			setResult(RESULT_SAVE, new Intent());
			finish();
		}
		return false;
	}
	
	/*
	 * Overridden method of interface TextWatcher.
	 * Called after each text change of the linked EditTexts.
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable s) {
		
		if(getCurrentFocus().equals(hiveName)) {
			newHive.setName(hiveName.getText().toString().trim());
		}
		else { // Nothing to be done YET
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
		intent 	= getIntent();
		front 	= new NewHiveFrontFragment();
		back 	= new NewHiveBackFragment();
		newHive = new Hive();
		hiveName = (EditText) getActionBar().getCustomView().findViewById(R.id.edit_text);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_fragment));
		map = mapFragment.getMap();
	}
	
	/*
	 * Initialized the map. Animates the camera to where
	 * the apiary is located and places a marker.
	 */
	private void initMap() {
		LatLng point = (LatLng) intent.getExtras()
			.get(NewApiaryActivity.INTENT_EXTRA_LAT_LNG);
		
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setAllGesturesEnabled(false);
		map.addMarker(new MarkerOptions().position(point));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
		
	}
	
	/*
	 * Sets up the listeners.
	 */
	private void initListeners() {
		hiveName.addTextChangedListener(this);
	}
	
	/*
	 * 
	 */
	private void initFrame() {
		getSupportFragmentManager().beginTransaction()
			.add(R.id.container, front)
			.commit();
		showingBack = false;
	}
	
	/*
	 * 
	 */
	private void swapFrame() {		
	    if (showingBack) {
	        getSupportFragmentManager().popBackStack();
	        showingBack = false;
	        return;
	    }

	    showingBack = true;

	    // Create and commit a new fragment transaction that adds the fragment for the back of
	    // the frame, uses custom animations, and is part of the fragment manager's back stack.
	    getSupportFragmentManager()
	            .beginTransaction()
	            .setCustomAnimations(
	                    R.anim.fade_in, R.anim.fade_out,
	                    R.anim.fade_in, R.anim.fade_out)
	            .replace(R.id.container, back)
	            .addToBackStack(null)
	            .commit();
	}
	
	/*
	 * It grabs clicks (taps) on the Edit Hive Details button
	 * in the front fragment and flips to the back.
	 */
	public void onButtonEditHiveDetailsClicked(View view) {
		swapFrame();
	}

}
