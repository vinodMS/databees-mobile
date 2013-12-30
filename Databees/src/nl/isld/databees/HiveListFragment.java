package nl.isld.databees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HiveListFragment extends ListFragment
	implements OnItemLongClickListener, Callback {
	
	private static final int REQUEST_CODE_NEW_HIVE = 0x01;
	
	/*
	 * Overridden method of class ListFragment.
	 * It just calls the parent method to create and
	 * return the ListView for our fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/*
	 * Overridden method of class ListFragment.
	 * It sets the ListView adapter.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setListAdapter(new HiveAdapter(getActivity(), AppCommon.HIVE_LOCAL_STORE));
		getListView().setOnItemLongClickListener(this);
	}
	
	/*
	 * Overridden method of class ListFragment.
	 * Called when an activity previously started has finished
	 * with a result. In this case it just notifies the adapter
	 * of potential change to the data, when the NewApiaryActivity
	 * returns a result.
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		((HiveAdapter) getListAdapter()).notifyDataSetChanged();
	}

	/*
	 * Overridden method of class ListFragment.
	 * Called when a list item is clicked (tapped).
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView parent, View clickedView,
			int position, long id) {
		
		if ((parent.getAdapter().getCount() - 1) == position) {
			startActivityForResult(new Intent(getActivity(),
					NewHiveActivity.class), REQUEST_CODE_NEW_HIVE);
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
		mode.getMenuInflater().inflate(R.menu.fragment_hive_list_action_mode, menu);
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
		for(int i = 0; i < (getListView().getCount() - 1); ++i) {
			getListView().setItemChecked(i, false);
		}
		getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
	}

	/*
	 * Overridden method of interface ActionMode.Callback.
	 * Called when the action mode is created. It sets the list
	 * to multiple selection mode.
	 * @see android.view.ActionMode.Callback#onPrepareActionMode(android.view.ActionMode, android.view.Menu)
	 */
	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		return true;
	}
	
	/*
	 * This method is internal. It is called by onActionItemClicked
	 * when the delete action has been clicked (tapped). It deletes
	 * all the selected items on the list and notifies the adapter
	 * that the data has changed. If nothing is selected, it displays
	 * an appropriate toast message.
	 */
	private void performActionDelete() {
		
		if(getListView().getCheckedItemCount() > 0) {
			SparseBooleanArray checkedItems = 
				getListView().getCheckedItemPositions();
			for(int i = 0; i < getListView().getCount() - 1; ++i) {
				if(checkedItems.get(i)) {
					getListView().setItemChecked(i, false);
					((HiveAdapter) getListAdapter())
						.remove((Hive) getListAdapter().getItem(i));
				}
			}
			((HiveAdapter) getListAdapter()).notifyDataSetChanged();
		}
		else {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.toast_nothing_to_delete),
					Toast.LENGTH_SHORT)
					.show();
		}
	}

}
