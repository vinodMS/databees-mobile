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

import nl.isld.databees.rss.RSSFeed;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity
	implements OnItemClickListener {
	
	private SlidingMenu				navDrawer;
	private ExpandableListView		navDrawerUserMenu;
	private ListView				navDrawerMenu;
	private UserExpandableAdapter	navDrawerUserMenuAdapter;
	private ArrayAdapter<String> 	navDrawerMenuAdapter;
	
	
	/*
	 * Overridden method of class SlidingFragmentActivity.
	 * Sets the content views for the main view and the
	 * navigation drawer, lying behind the first, and it
	 * initializes the activity by calling a series of
	 * private initialization methods.
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.navigation_drawer);
        
        initActionBar();
        initAttributes();
        initDrawer();
        initFrame();
        
        LocalStore.initOnce();
    }
    
    /*
     * Overridden method of class SlidingFragmentActivity.
     * Called whenever an item in the option menu is clicked.
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case android.R.id.home:
    		navDrawer.toggle();
    	}
    	
		return super.onOptionsItemSelected(item);
    }
    
    public void setMapMarker(LatLng position) {
    	MiniMapFragment map = 
    			(MiniMapFragment) getSupportFragmentManager().findFragmentByTag("MINI_MAP_FRAGMENT");
    	
    	if(map != null) {
    		map.moveMarker(position);
    	}
    }
    
    /*
     * Internal private method that initializes the action bar of
     * the current activity.
     */
    private void initActionBar() {
    	getActionBar().setDisplayShowCustomEnabled(true);
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);
    }
    
    /*
     * Internal private method that initializes all the attributes
     * declared in this activity.
     */
    private void initAttributes() {
    	navDrawer = getSlidingMenu();
    	navDrawerUserMenu = (ExpandableListView) navDrawer.findViewById(R.id.user_menu);
    	navDrawerMenu = (ListView) navDrawer.findViewById(R.id.menu);
    	navDrawerUserMenuAdapter = new UserExpandableAdapter
    			(getApplicationContext(),
    					"NBV",
    							AppCommon.USER_MENU_ITEMS);
    	navDrawerMenuAdapter = new ArrayAdapter<String>
    			(getApplicationContext(),
    					android.R.layout.simple_list_item_1,
    							AppCommon.NAV_MENU_ITEMS);
    }
    
    /*
     * Internal private method that initializes the navigation
     * drawer for this activity.
     */
    private void initDrawer() {
    	navDrawer.setShadowDrawable(R.drawable.drawer_shadow);
    	navDrawer.setShadowWidth(35);
    	navDrawer.setBehindOffset(120);
    	
    	navDrawerUserMenu.setAdapter(navDrawerUserMenuAdapter);
    	navDrawerMenu.setAdapter(navDrawerMenuAdapter);
    	navDrawerMenu.setOnItemClickListener(this);
    }
    
    /*
     * Internal private method that adds the initial fragment
     * to the activity's layout for display.
     */
    private void initFrame() {
    	getSupportFragmentManager().beginTransaction()
    		.add(R.id.main_container, new DashboardFragment())
    		.commit();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		for(Fragment fragment : getSupportFragmentManager().getFragments()) {
			Log.d("Beebug", "Fragment present: " + fragment.toString());
			getSupportFragmentManager().beginTransaction()
				.remove(fragment).commit();
		}
		
		switch(position) {
		
		case 0:
			getSupportFragmentManager().beginTransaction()
    		.add(R.id.main_container, new DashboardFragment())
    		.commit();
			break;
			
		case 1:
			getSupportFragmentManager().beginTransaction()
    		.add(R.id.main_container, new ApiaryListFragment())
    		.commit();
			break;
			
		case 2:
			getSupportFragmentManager().beginTransaction()
    		.add(R.id.main_container, new TaskListFragment())
    		.commit();
			break;
			
		case 3:
			getSupportFragmentManager().beginTransaction()
    		.add(R.id.main_container, new DiseaseFragment())
        		.commit();
			break;
		
		case 4:
			startActivity(new Intent(this, RSSFeed.class));
			break;
		
		case 5:
			getSupportFragmentManager().beginTransaction()
    		.add(R.id.main_container, new AboutFragment())
        	.commit();
			break;
		default:break;
		}
		
		navDrawer.toggle();
		
	}
	
	  @Override
	   public void onBackPressed() {
	       // do nothing.
	   }
}