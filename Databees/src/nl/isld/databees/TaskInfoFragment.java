package nl.isld.databees;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class TaskInfoFragment extends Fragment
	{
	
	public static final String	ARG_TASK_ID	=	"ARG_TASK_ID";
	public static final String	REQUEST_EXTRA_TASK_ID	= "REQUEST_EXTRA_TASK_ID";
	
	private Task					task;
	private TextView				taskDesc;
	private TextView				taskDate;
	private TextView				taskTitle;
	/*
	 * Overridden method of class Fragment.
	 * It returns the view to be used for the fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_task_info, container, false);
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState) {
		SimpleDateFormat df = new SimpleDateFormat("EEEE, dd MMMM yyyy");
		taskTitle = (TextView) getView().findViewById(R.id.task_title);
		taskDate = (TextView) getView().findViewById(R.id.task_date);
		taskDesc = (TextView) getView().findViewById(R.id.task_detail);
		
		
		task = (Task) LocalStore.findTaskById(getArguments().getString(ARG_TASK_ID));
		taskTitle.setText(task.getTitle());
		taskDate.setText(df.format(task.getTaskDate()));
		taskDesc.setText(task.getDesc());
			
		
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actions_basic, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		
		case R.id.action_edit:
			Log.d("Beebug", "I am supposed to edit now");
			Intent intent = new Intent(getActivity(), TaskActivity.class);
			intent.putExtra(TaskActivity.REQUEST_EXTRA_TASK_ID, task.getId());
			Log.d("Beebug", "I will edit this task with id :" + task.getId());
			startActivityForResult(intent, TaskActivity.REQUEST_EDIT_TASK);
		}
		
		return false;
	}
	
	
}
