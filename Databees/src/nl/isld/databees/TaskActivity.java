package nl.isld.databees;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;


public class TaskActivity extends FragmentActivity
	implements TextWatcher, OnClickListener, OnCheckedChangeListener {
	
	public static final int			REQUEST_NEW_TASK		= 100;
	public static final int			REQUEST_EDIT_TASK		= 101;
	public static final String		REQUEST_EXTRA_TASK_ID	= "REQUEST_EXTRA_TASK_ID";
	public static final String		RESULT_EXTRA_TASK_ID	= "RESULT_EXTRA_TASK_ID";

	private Task					task;
	private EditText				taskTitle;
	private EditText				taskDesc;
	private EditText				taskDate;	
	private ImageButton 			imagebutton;
	private Calendar 				cal;
	private int 					minute;
	private int 					hour;
	private int 					day;
	private int 					month;
	private int 					year;
	private EditText 				edit_task_calendar;
	private Switch					alarm_on;
	private EditText				reminderSpinner;
	private TextView 				textViewTime;
	private Long					getHour;
	
 
	static final int TIME_DIALOG_ID = 999;
	static final int DATE_DIALOG_ID = 899;
	
	//used for register alarm manager
    PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    BroadcastReceiver mReceiver;
	
    
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
		imagebutton = (ImageButton) findViewById(R.id.imageButton1);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        
		edit_task_calendar = (EditText) findViewById(R.id.edit_task_calendar);
		
		imagebutton.setOnClickListener(this);
	    
		
		Spinner hivesSpinner = (Spinner) findViewById(R.id.hives_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = HiveAdapter.createFromResource(this,
		        R.array.hives_spinner, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		hivesSpinner.setAdapter(adapter);
		
		reminderSpinner = (EditText) findViewById(R.id.reminder_spinner);
		
		alarm_on = (Switch) findViewById(R.id.switch1);
		
		if (alarm_on != null) {
		    alarm_on.setOnCheckedChangeListener(this);
		}
		
		//Register AlarmManager Broadcast receive.
        RegisterAlarmBroadcast();
        
        
		addButtonListener();
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
		else
			if(alarm_on.isChecked()){
				alarmManager.set( AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 20000, pendingIntent );
				Toast.makeText(getBaseContext(), "A reminder has been set", Toast.LENGTH_LONG).show();
			}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		showDialog(0);
	}

	/*
	 * Overridden onCheckedChanged method to pass the state of the 
	 * alarm on/off button and disable/enable the date/time picker
	 * based on the state
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    if(isChecked) {
	    	//enable date picker when Switch is ON
	    	reminderSpinner.setEnabled(true);
	    } else {
	    	//disable date picker when Switch is ON
	    	reminderSpinner.setEnabled(false);
	    }
	}
	
	public void showNotification(){

		// define sound URI, the sound to be played when there's a notification
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		
		// intent triggered, you can add other intent for other actions
		Intent intent = new Intent(TaskActivity.this, TaskListFragment.class);
		PendingIntent pIntent = PendingIntent.getActivity(TaskActivity.this, 0, intent, 0);
		
		// this is it, we'll build the notification!
		// in the addAction method, if you don't want any icon, just set the first param to 0
		Notification mNotification = new Notification.Builder(this)
			
			.setContentTitle("Task Reminder!")
			.setContentText("You have one task pending!")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pIntent)
			.setSound(soundUri)
			
			.build();
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// If you want to hide the notification after it was selected, do the code below
		// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notificationManager.notify(0, mNotification);
	}
	
	    private void RegisterAlarmBroadcast()
	    {
	          Log.i("Alarm Example:RegisterAlarmBroadcast()", "Going to register Intent.RegisterAlramBroadcast");
	 
	        //This is the call back function(BroadcastReceiver) which will be called when your 
	        //alarm time will reached.
	        mReceiver = new BroadcastReceiver()
	        {
	            private static final String TAG = "Alarm Example Receiver";
	            @Override
	            public void onReceive(Context context, Intent intent)
	            {
	                Log.i(TAG,"BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	                showNotification();
	                
	            }
	        };
	 
	        // register the alarm broadcast here
	        registerReceiver(mReceiver, new IntentFilter("nl.isld.databees") );
	        pendingIntent = PendingIntent.getBroadcast( this, 0, new Intent("nl.isld.databees"),0 );
	        alarmManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
	    }
	    
	    @Override
	    protected void onDestroy() {
	    	unregisterReceiver(mReceiver);
	    	super.onDestroy();
	    }
		
		public void addButtonListener() {
	 
			reminderSpinner.setOnClickListener(new OnClickListener() {
	 
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
	 
					showDialog(TIME_DIALOG_ID);
	 
				}
	 
			});
			
			imagebutton.setOnClickListener(new OnClickListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					
					showDialog(DATE_DIALOG_ID);
					
				}
			});
	 
		}
		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case TIME_DIALOG_ID:
				// set time picker as current time
				return new TimePickerDialog(this, timePickerListener, hour, minute,false);
				
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, datePickerListener, year, month, day);
			}
			return null;

		}
	 
		private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
				hour = selectedHour;
				minute = selectedMinute;
				textViewTime = (TextView) findViewById(R.id.reminder_spinner);
				// set current time into text view
				textViewTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
				Log.d("system","" + System.currentTimeMillis() + 1000);
			
				cal.setTimeInMillis(System.currentTimeMillis());
				cal.set(Calendar.HOUR_OF_DAY, hour);
				cal.set(Calendar.MINUTE, minute);
				cal.set(Calendar.SECOND, 0);
				Log.d("user","" + cal.getTimeInMillis() +1000);
				
				
			}
		};
		
		private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
				
				edit_task_calendar.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);
			}
		};
		
		private static String padding_str(int c) {
			if (c >= 10)
			   return String.valueOf(c);
			else
			   return "0" + String.valueOf(c);
		}
}
