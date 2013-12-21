package nl.isld.databees;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	
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
    					"Barack Obama",
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
    	navDrawer.findViewById(R.id.nav_drawer_main_layout)
    		.getBackground()
    		.setAlpha(80);
    	navDrawer.setShadowDrawable(R.drawable.drawer_shadow);
    	navDrawer.setShadowWidth(35);
    	navDrawer.setBehindOffset(120);
    	
    	navDrawerUserMenu.setAdapter(navDrawerUserMenuAdapter);
    	navDrawerMenu.setAdapter(navDrawerMenuAdapter);
    }
    
    /*
     * Internal private method that adds the initial fragment
     * to the activity's layout for display.
     */
    private void initFrame() {
    	getSupportFragmentManager().beginTransaction()
    		.add(R.id.container, new ApiaryListFragment())
    		.commit();
    }
}