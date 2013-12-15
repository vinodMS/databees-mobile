package nl.isld.databees;

import java.sql.Date;

public class Plant {
	
	public int ID;
	public String name, desc, photoUri;
	public Date blossoms, withers;
	
	public Plant(int ID, String desc, Date blossoms, Date withers) {
				
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public Date getBlossomDate() {
		return blossoms;
	}
	
	public Date getWitherDate() {
		return withers;
	}
	
	public String getPhotoUri() {
		return photoUri;
	}
}
