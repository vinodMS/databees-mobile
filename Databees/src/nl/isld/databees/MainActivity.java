package nl.isld.databees;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
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
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("Debug", "[MainActivity onCreate]");
        super.onCreate(savedInstanceState);
        
        Log.d("Debug", "Setting content view");
        setContentView(R.layout.activity_main);
        Log.d("Debug", "Setting navigation drawer content view");
        setBehindContentView(R.layout.navigation_drawer);
        
        Log.d("Debug", "Initializing attributes");
        initAttributes();
        Log.d("Debug", "Initializing action bar");
        initActionBar();
        Log.d("Debug", "Initializing drawer");
        initDrawer();   
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case android.R.id.home:
    		navDrawer.toggle();
    	}
    	
		return super.onOptionsItemSelected(item);
    }
    
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
    
    private void initActionBar() {
    	ActionBar actionBar = getActionBar();
    	
    	actionBar.setDisplayShowCustomEnabled(true);
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    }
    
    private void initDrawer() {
    	navDrawer.findViewById(R.id.nav_drawer_main_layout)
    		.getBackground()
    		.setAlpha(80);
    	navDrawer.setShadowDrawable(R.drawable.drawer_shadow);
    	navDrawer.setShadowWidth(20);
    	navDrawer.setBehindOffset(120);
    	
    	navDrawerUserMenu.setAdapter(navDrawerUserMenuAdapter);
    	navDrawerMenu.setAdapter(navDrawerMenuAdapter);
    }
}