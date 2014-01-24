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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class HiveListFragment extends ListFragment
	implements OnItemLongClickListener, Callback {
	
	public static final String	ARG_APIARY_ID	=	"ARG_APIARY_ID";
	
	private Apiary apiary;
	private boolean actionMode = false;
	
	/*
	 * Overridden method of class ListFragment.
	 * It just calls the parent method to create and
	 * return the ListView for our fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		setHasOptionsMenu(true);
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
		
		apiary = (Apiary) LocalStore.findApiaryById(
				getArguments().getString(ARG_APIARY_ID));
		
		setListAdapter(new HiveAdapter(getActivity(), apiary.getHives()));
		getListView().setOnItemLongClickListener(this);
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
			Intent intent = new Intent(getActivity(), ApiaryActivity.class);
			intent.putExtra(ApiaryActivity.REQUEST_EXTRA_APIARY_ID, apiary.getId());
			startActivityForResult(intent, ApiaryActivity.REQUEST_EDIT_APIARY);
			
		}
		
		return false;
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
		
		if(resultCode == Activity.RESULT_OK) {
			switch(requestCode) {
			
			case HiveActivity.REQUEST_NEW_HIVE:
				Hive hive = LocalStore.findHiveById(
						data.getStringExtra(HiveActivity.RESULT_EXTRA_HIVE_ID));
				apiary.addHive(hive);
				break;
				
			case ApiaryActivity.REQUEST_EDIT_APIARY:
				((MainActivity) getActivity()).setMapMarker(apiary.getLocation());
				break;
				
			default:break;
			}
		}
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
			Intent intent =
					new Intent(getActivity(), HiveActivity.class)
					.putExtra(HiveActivity.REQUEST_EXTRA_LOCATION, apiary.getLocation());
			
			startActivityForResult(intent, HiveActivity.REQUEST_NEW_HIVE);
		}
		else {
			
			if(actionMode) {
				return;
			}
			
			InspectionListFragment	inspectionList	= new InspectionListFragment();
			HiveImageFragment		hiveImage		= new HiveImageFragment();
			
			Hive	hive	= (Hive) parent.getAdapter().getItem(position);
			Bundle	args	= new Bundle();
			
			args.putString(InspectionListFragment.ARG_HIVE_ID, hive.getId());
			inspectionList.setArguments(args);
			
			getActivity().getSupportFragmentManager().beginTransaction()
				.add(R.id.top_container, hiveImage)
				.replace(R.id.main_container, inspectionList)
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
		mode.getMenuInflater().inflate(R.menu.fragment_hive_list_action_mode, menu);
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
