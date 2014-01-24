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

import com.google.android.gms.maps.model.LatLng;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ApiaryListFragment extends ListFragment
	implements OnItemLongClickListener, Callback {
	
	public static final String	BACKSTACK_LABEL = "APIARY_LIST_FRAGMENT";
	
	public boolean actionMode = false;

	
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
		setListAdapter(new ApiaryAdapter(getActivity(), LocalStore.APIARY_LIST));
		getListView().setOnItemLongClickListener(this);
	}
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		intent.putExtra("REQUEST_CODE", requestCode);
		super.startActivityForResult(intent, requestCode);
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
		((ApiaryAdapter) getListAdapter()).notifyDataSetChanged();
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
			Intent intent = new Intent(getActivity(), ApiaryActivity.class);
			startActivityForResult(intent, ApiaryActivity.REQUEST_NEW_APIARY);
		}
		else {
			
			if(actionMode) {
				return;
			}
			
			ApiaryInfoFragment	apiaryInfo	= new ApiaryInfoFragment();
			HiveListFragment	hiveList	= new HiveListFragment();
			
			Apiary	apiary	= (Apiary) parent.getAdapter().getItem(position);
			Bundle	args	= new Bundle();
			
			args.putString(HiveListFragment.ARG_APIARY_ID, apiary.getId());
			args.putParcelable(ApiaryInfoFragment.ARG_LOCATION, apiary.getLocation());
			apiaryInfo.setArguments(args);
			hiveList.setArguments(args);
			
			getActivity().getSupportFragmentManager().beginTransaction()
				.add(R.id.top_container, MiniMapFragment.newInstance(apiary.getLocation()),
						"MINI_MAP_FRAGMENT")
				.add(R.id.middle_container, apiaryInfo)
				.replace(R.id.main_container, hiveList)
				.addToBackStack(null)
				.commit();
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
		mode.getMenuInflater().inflate(R.menu.fragment_apiary_list_action_mode, menu);
		return actionMode = true;
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
		actionMode = false;
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
					((ApiaryAdapter) getListAdapter())
						.remove((Apiary) getListAdapter().getItem(i));
				}
			}
			((ApiaryAdapter) getListAdapter()).notifyDataSetChanged();
		}
		else {
			Toast.makeText(getActivity(),
					getResources().getString(R.string.toast_nothing_to_delete),
					Toast.LENGTH_SHORT)
					.show();
		}
	}

}
