package nl.isld.databees;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HiveActivity extends FragmentActivity
	implements TextWatcher {
	
	public static final int			REQUEST_NEW_HIVE		= 100;
	public static final int			REQUEST_EDIT_HIVE		= 101;
	public static final int 		REQUEST_CAPTURE_IMAGE	= 900;
	public static final String		REQUEST_EXTRA_LOCATION	= "REQUEST_EXTRA_LOCATION";
	public static final String		RESULT_EXTRA_HIVE_ID	= "RESULT_EXTRA_HIVE_ID";

	private	HiveFrontFragment		front;
	private HiveBackFragment		back;
	private boolean					showingBack;
	
	private Hive					hive;
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
		setContentView(R.layout.activity_hive);
		
		initAttributes();
		initActionbar();
		initMap();
		initListeners();
		initFrame();
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the options menu for the NewHiveActivity activity.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_hive_actions, menu);
	    return true;
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		intent.putExtra("REQUEST_CODE", requestCode);
		super.startActivityForResult(intent, requestCode);
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Overridden method finish() terminates the activity
	 * returning an intent that contains the newly created
	 * hive.
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		
		if(getIntent().getIntExtra("REQUEST_CODE", 0)
				== REQUEST_NEW_HIVE) {
			LocalStore.HIVE_LIST.add(hive);
		}
		
		Intent intent = new Intent();
		intent.putExtra(RESULT_EXTRA_HIVE_ID, hive.getId());
		
		setResult(RESULT_OK, intent);
		super.finish();
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
			// Overridden method finish() terminates the activity
			// returning an intent that contains the newly created
			// hive. If you want to finish the activity without
			// a result call super.finish()
			if(checkRequired()) {
				finish();
			}
		default:
			super.finish();
		}
		return false;
	}
	
	/*
	 * Overridden method of interface TextWatcher.
	 * Called after each text change of the linked EditTexts.
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable editable) {
		
		if(getCurrentFocus().equals(hiveName)) {
			hive.setName(editable.toString().trim());
		}
		else if(getCurrentFocus().getId() == R.id.hive_notes_input) {
			hive.setNotes(editable.toString().trim());
		}
		else if(getCurrentFocus().getId() == R.id.suppers_value_input) {
			if(editable.toString().length() > 0) {
				hive.setNumberOfSuppers(Integer.valueOf(editable.toString()));
			}
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch(requestCode) {
		case REQUEST_CAPTURE_IMAGE:
			if(resultCode == RESULT_OK) {
				ImageView hiveImage = (ImageView) findViewById(R.id.hive_image);
				hiveImage.setImageBitmap((Bitmap) data.getExtras().get("data"));
				Toast.makeText(getApplicationContext(), "Image saved to: " + data.getData(), Toast.LENGTH_SHORT).show();
				break;
			}
		case InspectionActivity.REQUEST_NEW_INSPECTION:
			Inspection inspection = LocalStore.findInspectionById(
					data.getStringExtra(InspectionActivity.RESULT_EXTRA_INSPECTION_ID));
			hive.getColony().addInspection(inspection);
			finish();
		}
	}
	
	/*
	 * It grabs clicks (taps) on the Edit Hive Details button
	 * in the front fragment and flips to the back.
	 */
	public void onButtonEditHiveDetailsClicked(View view) {
		swapFrame();
	}
	
	/*
	 * Called when the user clicks (taps) on the Add Inspections
	 * button. If the required fields are filled in, then a new
	 * activity is started to create an inspection.
	 */
	public void onButtonAddInspectionsClicked(View view) {
		if(checkRequired()) {
			startActivityForResult(new Intent(this, InspectionActivity.class),
					InspectionActivity.REQUEST_NEW_INSPECTION);
		}
	}
	
	/*
	 * Called when the user clicks (taps) on the hive image.
	 * A new camera activity is created, allowing the user to
	 * capture an image. After that the control returns to this
	 * activity and the new hive image is displayed.
	 */
	public void onHiveImageClicked(View view) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				getDir("private", MODE_PRIVATE).toURI());
		startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
	}
	
	/*
	 * Initializes the activity's action bar.
	 */
	private void initActionbar() {
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setCustomView(hiveName);
	}
	
	/*
	 * Sets the values of all attributes.
	 */
	private void initAttributes() {
		hive = new Hive();
		hiveName = new EditText(this);
		hiveName.setHint(getResources().getString(R.string.hint_new_apiary_name));
		hiveName.setEms(0);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_fragment));
		map = mapFragment.getMap();
		
		front 	= new HiveFrontFragment();
		back 	= new HiveBackFragment();
	}
	
	/*
	 * Initialized the map. Animates the camera to where
	 * the apiary is located and places a marker.
	 */
	private void initMap() {
		LatLng location = getIntent().getExtras().getParcelable(REQUEST_EXTRA_LOCATION);
		
		map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setAllGesturesEnabled(false);
		map.addMarker(new MarkerOptions().position(location));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
		
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
			.add(R.id.main_container, front)
			.commit();
		showingBack = false;
	}
	
	/*
	 * Swaps the front and the back fragments with
	 * animation accordingly
	 */
	private void swapFrame() {		
	    if(showingBack) {
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
	            .replace(R.id.main_container, back)
	            .addToBackStack(null)
	            .commit();
	}
	
	/*
	 * Checks if the required information about the
	 * hive is given by the user. It also displays
	 * a toast prompt message if something is missing.
	 * @returns		true if all requirements are met,
	 * 				otherwise return false
	 */
	private boolean checkRequired() {
		
		if(hive.getName().equals(new String())) {
			Toast.makeText(getApplicationContext(), 
					getResources().getString(R.string.toast_prompt_hive_name),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}

}
