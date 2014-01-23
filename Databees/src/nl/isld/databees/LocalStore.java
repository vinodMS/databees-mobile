package nl.isld.databees;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class LocalStore {
	
	public static final List<Apiary> 		APIARY_LIST		= new ArrayList<Apiary>();
	public static final List<Hive> 			HIVE_LIST		= new ArrayList<Hive>();
	public static final List<Inspection>	INSPECTION_LIST	= new ArrayList<Inspection>();
	public static final List<Task>			TASK_LIST		= new ArrayList<Task>();
	
	public static int APIARY_ID_INCR			= 0;
	public static int HIVE_ID_INCR				= 0;
	public static int INSPECTION_ID_INCR		= 0;
	public static int COLONY_ID_INCR			= 0;
	public static int TASK_ID_INCR				= 0;
	
	public static boolean initialized = false;
	
	public static void initOnce() {
		
		if(initialized) {
			return;
		}
		
		Apiary apiary = new Apiary(
				"The Apiary",
				new LatLng(52.4500, 4.8333),
				"The rise of the testers group");
		Hive hive = new Hive(apiary, "The Hive");
		
		InspectionParameters parameters = new InspectionParameters();
		parameters.eggs = true;
		parameters.queenSeen = true;
		Inspection inspection = new Inspection(hive.getColony(), new Date(),
				"Testers season is coming", parameters);
		
		Task task = new Task("Clean Hive Entrance", "If there is heavy snow, make certain the entrance to the hive is cleared to allow for proper ventilation.", Calendar.getInstance().getTime());
		
		TASK_LIST.add(task);
		APIARY_LIST.add(apiary);
		HIVE_LIST.add(hive);
		INSPECTION_LIST.add(inspection);
		
		initialized = true;
	}
	
	public static String generateApiaryId() {
		return "AID" + (++APIARY_ID_INCR);
	}
	
	public static String generateHiveId() {
		return "HID" + (++APIARY_ID_INCR);
	}
	
	public static String generateInspectionId() {
		return "IID" + (++INSPECTION_ID_INCR);
	}
	
	public static String generateColonyId() {
		return "CID" + (++COLONY_ID_INCR);
	}
	
	public static String generateTaskId() {
		return "TID" + (++TASK_ID_INCR);
	}
	
	public static Apiary findApiaryById(String id) {
		
		for(Apiary apiary : APIARY_LIST) {
			if(apiary.getId().equals(id)) {
				return apiary;
			}
		}
		return null;
	}
	
	public static Task findTaskById(String id) {
		
		for(Task task : TASK_LIST) {
			if(task.getId().equals(id)) {
				return task;
			}
		}
		return null;
	}

	public static Hive findHiveById(String apiaryId, String id) {
		Apiary apiary = findApiaryById(apiaryId);
		
		for(Hive hive : apiary.getHives()) {
			if(hive.getId().equals(id)) {
				return hive;
			}
		}
		return null;
	}
	
	public static Hive findHiveById(String id) {
		for(Hive hive : HIVE_LIST) {
			if(hive.getId().equals(id)) {
				return hive;
			}
		}
		return null;
	}
	
	public static Colony findColonyById(String hiveId, String id) {
		Hive hive = null;
		Colony colony = null;
		
		if((hive = findHiveById(hiveId)) != null) {
			if((colony = hive.getColony()) != null) {
				while(colony.getId() != id) {
					colony = colony.getParent();
				}
			}
		}
		return colony;
	}
	
	public static Colony findColonyById(String id) {
		Colony colony = null;
		
		for(Apiary apiary : APIARY_LIST) {
			for(Hive hive : apiary.getHives()) {
				colony = hive.getColony();
				
				while(colony.getId() != id) {
					colony = colony.getParent();
				}
			}
		}
		return colony;
	}
	
	public static Inspection findInspectionById(String hiveOrColonyId, String id) {
		Hive hive;	Colony colony;
		
		if((hive = findHiveById(hiveOrColonyId)) != null) {
			for(colony = hive.getColony();
					colony.getParent() != null; 
					colony = colony.getParent()) {
				for(Inspection inspection : colony.getInspections()) {
					if(inspection.getId().equals(id)) {
						return inspection;
					}
				}
			}
		} else {
			colony = findColonyById(hiveOrColonyId);
			for(Inspection inspection : colony.getInspections()) {
				if(inspection.getId().equals(id)) {
					return inspection;
				}
			}
		}
		return null;
	}
	
	public static Inspection findInspectionById(String id) {
		for(Inspection inspection : INSPECTION_LIST) {
			if(inspection.getId().equals(id)) {
				return inspection;
			}
		}
		return null;
	}
}
