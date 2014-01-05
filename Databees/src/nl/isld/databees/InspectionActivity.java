package nl.isld.databees;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;

public class InspectionActivity extends FragmentActivity
	implements ActionBar.TabListener, TextWatcher {
	
	public static final int			REQUEST_NEW_INSPECTION		= 100;
	public static final int			REQUEST_EDIT_INSPECTION		= 101;
	public static final String		RESULT_EXTRA_INSPECTION_ID	= "RESULT_EXTRA_INSPECTION_ID";
	
	private int[] TAB_LABEL_IDS = {
			R.string.label_general_ispection_tab,
			R.string.label_feeding_ispection_tab,
			R.string.label_diseases_ispection_tab
	};
	
	private ViewPager			viewPager;
	private TabsPagerAdapter	tabsPagerAdapter;
	
	private Inspection	inspection;
	
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
	
	@Override
	public void finish() {
		
		if(getIntent().getIntExtra("REQUEST_CODE", 0)
				== REQUEST_NEW_INSPECTION) {
			LocalStore.INSPECTION_LIST.add(inspection);
		} else {
			// TODO
		}
		
		Intent intent = new Intent();
		intent.putExtra(RESULT_EXTRA_INSPECTION_ID, inspection.getId());
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
		if(checkRequired()) {
			switch(item.getItemId()) {
			case R.id.action_save:	finish();
									return true;
			}
		}
		return false;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
		// TODO
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		// TODO
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
		// TODO 
	}
	
	@Override
	public void afterTextChanged(Editable editable) {
		// TODO Auto-generated method stub
		
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
	
	private void initAttributes() {
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(tabsPagerAdapter);
		
		if(getIntent().getIntExtra("REQUEST_CODE", REQUEST_NEW_INSPECTION)
				== REQUEST_NEW_INSPECTION) {
			inspection = new Inspection();
		} else {
			// TODO
		}
	}
	
	private void initActionBar() {
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for(int tabLabelId : TAB_LABEL_IDS) {
			getActionBar().addTab(getActionBar().newTab().setText(tabLabelId)
				.setTabListener(this));
		}
	}
	
	private boolean checkRequired() {
		return true;
	}

}
