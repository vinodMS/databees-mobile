package nl.isld.databees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ApiariesFragment extends Fragment
	implements OnItemClickListener, OnItemLongClickListener, Callback {
	
	private static final int REQUEST_CODE_NEW_APIARY = 0x01;
	
	private ListView 					apiaries;
	private ApiaryAdapter				apiariesAdapter;
	
	/*
	 * Overridden method of class Fragment.
	 * It creates the fragment view and returns it to the
	 * activity that uses the fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.d("Debug", "[ApiariesFragment onCreateView]");
		
		Log.d("Debug", "Inflating view");
		View view = inflater.inflate(R.layout.fragment_apiaries, container);
		
		Log.d("Debug", "Initializing attributes");
		apiariesAdapter = new ApiaryAdapter(getActivity(), AppCommon.APIARY_LOCAL_STORE);
		apiaries = (ListView) view.findViewById(R.id.apiaries_list);
		apiaries.setAdapter(apiariesAdapter);
		apiaries.setOnItemClickListener(this);
		apiaries.setOnItemLongClickListener(this);
		
		Log.d("Debug", "Returning view");
		return view;
	}
	
	/*
	 * Overridden method of class Fragment.
	 * Called when an activity previously started has finished
	 * with a result. In this case it just notifies the adapter
	 * of potential change to the data. 
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		apiariesAdapter.notifyDataSetChanged();
	}

	/*
	 * Overridden method of interface OnItemClickListener.
	 * Called when a list item is clicked (tapped).
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View clickedView,
			int position, long id) {
		
		if ((parent.getAdapter().getCount() - 1) == position) {
			startActivityForResult(new Intent(getActivity(),
					NewApiaryActivity.class), REQUEST_CODE_NEW_APIARY);
		}
		else {
			// TO FILL IN
		}
	}

	/*
	 * Overridden method of interface OnItemLongClickListener.
	 * Called when a list item is long clicked (tapped). It sets
	 * the activity into action mode, showing the contextual action bar.
	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View clickedView,
			int position, long id) {
		if ((parent.getAdapter().getCount() - 1) != position) {
			getActivity().startActionMode(this);
			return true;
		}
		return false;
	}

	/*
	 * Overridden method of interface ActionMode.Callback.
	 * Called when the action mode is started by the user's long
	 * click (tap) on a list item. It inflates the menu with the
	 * actions to use specifically when in action mode.
	 * @see android.view.ActionMode.Callback#onCreateActionMode(android.view.ActionMode, android.view.Menu)
	 */
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		mode.getMenuInflater().inflate(R.menu.activity_main_apiaries_action_mode, menu);
		return true;
	}
	
	/*
	 * Overridden method of interface ActionMode.Callback.
	 * Called when an action is clicked (tapped) when in action mode.
	 * Note that actions differ between normal and action mode. 
	 * @see android.view.ActionMode.Callback#onActionItemClicked(android.view.ActionMode, android.view.MenuItem)
	 */
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.action_delete:
			performActionDelete();	return true;
		}
		
		return false;
	}

	/*
	 * Overridden method of interface ActionMode.Callback.
	 * Called when the user leaves the action mode. It resets the
	 * list to its normal state.
	 * @see android.view.ActionMode.Callback#onDestroyActionMode(android.view.ActionMode)
	 */
	@Override
	public void onDestroyActionMode(ActionMode mode) {
		for(int i = 0; i < (apiaries.getCount() - 1); ++i) {
			apiaries.setItemChecked(i, false);
		}
		apiaries.setChoiceMode(ListView.CHOICE_MODE_NONE);
	}

	/*
	 * Overridden method of interface ActionMode.Callback.
	 * Called when the action mode is created. It sets the list
	 * to multiple selection mode.
	 * @see android.view.ActionMode.Callback#onPrepareActionMode(android.view.ActionMode, android.view.Menu)
	 */
	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		apiaries.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		return false;
	}
	
	/*
	 * This method is internal. It is called by onActionItemClicked
	 * when the delete action has been clicked (tapped). It deletes
	 * all the selected items on the list and notifies the adapter
	 * that the data has changed. If nothing is selected, it displays
	 * an appropriate toast message.
	 */
	private void performActionDelete() {
		
		if(apiaries.getCheckedItemCount() > 0) {
			SparseBooleanArray checkedItems = 
				apiaries.getCheckedItemPositions();
			for(int i = 0; i < apiaries.getCount() - 1; ++i) {
				if(checkedItems.get(i)) {
					apiaries.setItemChecked(i, false);
					apiariesAdapter.remove(apiariesAdapter.getItem(i));
				}
			}
			apiariesAdapter.notifyDataSetChanged();
		}
		else {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.toast_nothing_to_delete),
					Toast.LENGTH_SHORT)
					.show();
		}
	}

}
