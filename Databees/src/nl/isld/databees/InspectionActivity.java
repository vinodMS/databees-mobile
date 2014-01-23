package nl.isld.databees;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.isld.databees.InspectionParameters.Temper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;

public class InspectionActivity extends FragmentActivity
	implements ActionBar.TabListener, TextWatcher,
	OnCheckedChangeListener, OnPageChangeListener,
	Spinner.OnItemSelectedListener {
	
	public static final int			REQUEST_NEW_INSPECTION		= 100;
	public static final int			REQUEST_EDIT_INSPECTION		= 101;
	public static final String		REQUEST_EXTRA_INSPECTION_ID = "REQUEST_EXTRA_INSPECTION_ID";
	public static final String		RESULT_EXTRA_INSPECTION_ID	= "RESULT_EXTRA_INSPECTION_ID";
	
	private String day		= new String();
	private String month	= new String();
	private String year		= new String();
	
	private int[] TAB_LABEL_IDS = {
			R.string.label_general_ispection_tab,
			R.string.label_queen_ispection_tab,
			R.string.label_diseases_ispection_tab
	};
	
	private ViewPager			viewPager;
	private TabsPagerAdapter	tabsPagerAdapter;
	
	private Inspection				inspection;
	private InspectionParameters	parameters;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspection);
		
		initAttributes();
		initActionBar();
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the options menu for the NewApiaryActivity activity.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_inspection_actions, menu);
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
	public void finish() {
		// Only if required fields are filled in
		// proceed with finishing the activity.
		if(checkRequired()) {
			
			try {
				if(getIntent().getIntExtra("REQUEST_CODE", 0)
						== REQUEST_NEW_INSPECTION) {
					inspection.setDate(constructDate());
					inspection.setParameters(parameters);
					LocalStore.INSPECTION_LIST.add(inspection);
				} else {
					inspection.setDate(constructDate());
					inspection.setParameters(parameters);
				}
			} catch(ParseException e) {
				Toast.makeText(this, "Give correct date", Toast.LENGTH_SHORT)
					.show();
				return;
			}
			
			Intent intent = new Intent();
			intent.putExtra(RESULT_EXTRA_INSPECTION_ID, inspection.getId());
			setResult(RESULT_OK, intent);
			
			super.finish();
		}
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
		// TODO
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		viewPager.setCurrentItem(tab.getPosition(), true);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
		// TODO 
	}
	
	@Override
	public void onPageScrollStateChanged(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int index, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int index) {
		getActionBar().setSelectedNavigationItem(index);
		
	}
	
	@Override
	public void afterTextChanged(Editable editable) {
		switch(getCurrentFocus().getId()) {
		
		case R.id.notes_input:
			inspection.setNotes(editable.toString().trim());		break;
			
		case R.id.queen_origin_input:
			parameters.queenOrigin = editable.toString().trim();	break;
		
		case R.id.disease_name:
			parameters.disease	= editable.toString().trim();		break;
			
		case R.id.other_descr:
			parameters.other	= editable.toString().trim();		break;
			
		case R.id.day_input:
			
			day = editable.toString();	Log.d("Beebug", "day: " + day);							break;
			
		case R.id.month_input:
			
			month = editable.toString(); Log.d("Beebug", "month: " + month);							break;
			
		case R.id.year_input:
			
			year = editable.toString();	Log.d("Beebug", "year: " + year);							break;
			
		default:break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence string, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence string, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCheckedChanged(CompoundButton button, boolean checked) {

		switch(button.getId()) {
		
		case R.id.eggs_present_input:
			parameters.eggs = checked;			break;
			
		case R.id.polen_in_input:
			parameters.polen = checked;			break;
			
		case R.id.queen_seen_input:
			parameters.queenSeen = checked;		break;
			
		case R.id.queen_marked_input:
			parameters.queenMarked = checked;	break;
		
		default:break;
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String item = (String) parent.getItemAtPosition(position);
		parameters.temper = Temper.valueOf(item);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		parameters.temper = Temper.Undefined;
		
	}
	
	public void onButtonDiseasesGuideClicked(View view) {
		// TODO
		
	}
	
	private void initAttributes() {
		int requestCode = getIntent().getIntExtra("REQUEST_CODE", 0);
		
		if(requestCode == REQUEST_NEW_INSPECTION) {
			inspection = new Inspection();
			parameters = new InspectionParameters();
		} else {
			inspection = LocalStore.findInspectionById(
					getIntent().getStringExtra(REQUEST_EXTRA_INSPECTION_ID));
			parameters = inspection.getParameters();
		}
		
		viewPager			= (ViewPager) findViewById(R.id.view_pager);
		viewPager.setOnPageChangeListener(this);
		tabsPagerAdapter	= new TabsPagerAdapter(getSupportFragmentManager(), inspection, requestCode);
		viewPager.setAdapter(tabsPagerAdapter);
		
	}
	
	private void initActionBar() {
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for(int tabLabelId : TAB_LABEL_IDS) {
			getActionBar().addTab(getActionBar().newTab().setText(tabLabelId)
				.setTabListener(this));
		}
	}
	
	private Date constructDate() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = new String("%d/%m/%y");
		
		dateString = dateString.replace("%d", day)
								.replace("%m", month)
								.replace("%y", year);
		
		return formatter.parse(dateString);
	}
	
	private boolean checkRequired() {
		
		if(day.length() > 0 && month.length() > 0 && year.length() > 0) {
			return true;
		}
		return false;
	}

}
