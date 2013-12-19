package nl.isld.databees;

import java.util.ArrayList;
import java.util.List;

/*
 * Class AppCommon
 * This is a static class, containing static information
 * used throughout all the aspects of the application.
 */
public class AppCommon {
	
	public static final List<Apiary> 	APIARY_LOCAL_STORE	= new ArrayList<Apiary>();
	public static final List<Hive>		HIVE_LOCAL_STORE	= new ArrayList<Hive>();

	public static final String[] NAV_MENU_ITEMS = {	"Dashboard",
													"Apiaries",
													"Tasks",
													"Flora",
													"Diseases" };
	
	public static final String[] USER_MENU_ITEMS = { "Edit",
													 "Logout" };
	
}
