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

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

public class TaskActivity extends FragmentActivity
	implements TextWatcher, OnClickListener {
	
	public static final int			REQUEST_NEW_TASK		= 100;
	public static final int			REQUEST_EDIT_TASK		= 101;
	public static final String		REQUEST_EXTRA_TASK_ID	= "REQUEST_EXTRA_TASK_ID";
	public static final String		RESULT_EXTRA_TASK_ID	= "RESULT_EXTRA_TASK_ID";

	private Task					task;
	private EditText				taskTitle;
	private EditText				taskDesc;
	private EditText				taskDate;	
	private ImageButton 			ib;
	private Calendar 				cal;
	private int 					day;
	private int 					month;
	private int 					year;
	private EditText 				et;
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the NewTaskActivity activity.
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);
		
		initActionbar();
		initAttributes();
		initListeners();
		
		// mDateButton = (Button) findViewById(R.id.date_button);
		ib = (ImageButton) findViewById(R.id.imageButton1);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		et = (EditText) findViewById(R.id.edit_task_calendar);
		ib.setOnClickListener(this);
	
		
		Spinner hivesSpinner = (Spinner) findViewById(R.id.hives_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = HiveAdapter.createFromResource(this,
		        R.array.hives_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		hivesSpinner.setAdapter(adapter);
		
		Spinner reminderSpinner = (Spinner) findViewById(R.id.reminder_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter1 = HiveAdapter.createFromResource(this,
		        R.array.reminder_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		reminderSpinner.setAdapter(adapter1);
	}
	
	/*
	 * Overridden method of class FragmentActivity.
	 * Creates the options menu for the NewTaskActivity activity.
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity_task_actions, menu);
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
	
	
	/*
	 * Overridden method of interface TextWatcher.
	 * Called after each text change of the linked EditTexts.
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable s) {

		if(getCurrentFocus().equals(taskTitle)) {
			task.setTitle(taskTitle.getText().toString().trim());
		}
		else if(getCurrentFocus().equals(taskDesc)) {
			task.setDesc(taskDesc.getText().toString().trim());
		}
		else if(getCurrentFocus().equals(taskDate)) {
			task.setTaskDate(taskDate.getText().toString().trim());
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
	 * Saves the task to the store and finishes the activity.
	 * Note that is should always be called after a call to
	 * checkRequired() and only if the latter returns true.
	 */
	@Override
	public void finish() {
		
		switch(getIntent().getIntExtra("REQUEST_CODE", 0)) {
		
		case REQUEST_NEW_TASK:
			LocalStore.TASK_LIST.add(task);
			
		case REQUEST_EDIT_TASK:
			// TODO
			break;
		}
		
		Intent intent = new Intent();
		intent.putExtra(RESULT_EXTRA_TASK_ID, task.getId());
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
		getActionBar().setCustomView(R.layout.actionbar_new_task);
	}
	
	/*
	 * Sets the values of all attributes.
	 */
	private void initAttributes() {
		taskTitle = (EditText) getActionBar().getCustomView().findViewById(R.id.edit_text2);
		taskDesc = (EditText) findViewById(R.id.desc_edit_text);
		taskDate = (EditText) findViewById(R.id.edit_task_calendar);
		Log.d("Beebug", "i have been caled" + (getIntent().getIntExtra("REQUEST_CODE", 0)));
		switch(getIntent().getIntExtra("REQUEST_CODE", 0)) {
		
		case REQUEST_NEW_TASK:
			Log.d("Beebug", "i'm creating a new one");
			task = new Task();
			break;
			
		case REQUEST_EDIT_TASK:
			Log.d("Beebug", "i'm editing");
			task = LocalStore.findTaskById(
					getIntent().getStringExtra(REQUEST_EXTRA_TASK_ID));
			if(task != null) {
				taskTitle.setText(task.getTitle());
				taskDesc.setText(task.getDesc());
				taskDate.setText((CharSequence) task.getTaskDate());
			}
			break;
		}
	}
	
	/*
	 * Sets up the listeners.
	 */
	private void initListeners() {
		taskTitle.addTextChangedListener(this);
		taskDesc.addTextChangedListener(this);
		taskDate.addTextChangedListener(this);
	}
	
	
	/*
	 * Checks if the required information about the
	 * hive is given by the user. It also displays
	 * a toast prompt message if something is missing.
	 * @returns		true if all requirements are met,
	 * 				otherwise return false
	 */
	private boolean checkRequired() {
		
		if(task.getTitle().equals(new String())) {
			Toast.makeText(getApplicationContext(), 
					getResources().getString(R.string.toast_prompt_task_title),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		showDialog(0);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};
}
