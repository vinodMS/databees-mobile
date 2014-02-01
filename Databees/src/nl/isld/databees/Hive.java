package nl.isld.databees;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Hive extends Object implements AccessProxy {
	
	private Apiary		apiary;
	private String		id;
	private String		name;
	private String		notes;
	private int			suppers;
	private Colony		colony;
	
	public Hive() {
		this.id			= LocalStore.generateHiveId();
		this.name		= new String();
		this.colony		= new Colony(this);
		this.apiary		= null;
		this.colony		= new Colony(this);
	}
	
	public Hive(Apiary apiary, String name) {
		this.id			= LocalStore.generateHiveId();
		this.name		= name;
		this.apiary		= apiary;
		this.colony		= new Colony(this);
		
		this.apiary.addHive(this);
	}
	
	public Apiary getApiary() {
		return apiary;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public int getNumberOfSuppers() {
		return suppers;
	}
	
	public Colony getColony() {
		return colony;
	}
	
	public List<Inspection> getInspections() {
		List<Inspection> inspections = colony.getInspections();
		Colony colony = this.colony;
		
		while((colony = colony.getParent()) != null) {
			inspections.addAll(colony.getInspections());
		}
		
		return inspections;
	}
	
	public Date getLastInspectionDate() {
		List<Inspection> inspections = colony.getInspections();
		
		if(inspections.size() > 0) {
			return inspections.get(inspections.size() - 1).getDate();
		} else {
			return null;
		}
	}
	
	public void setApiary(Apiary apiary) {
		this.apiary = apiary;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setNumberOfSuppers(int suppers) {
		if(suppers >= 0) {
			this.suppers = suppers;
		}
	}
	
	public void setColony(Colony colony) {
		this.colony	= colony;
	}
	
	public void moveToApiary(Apiary apiary) {
		this.apiary.removeHive(this);
		apiary.addHive(this);
		this.apiary = apiary;
	}

	@Override
	public JSONObject translate() throws JSONException {
		JSONObject json = new JSONObject();
		
		json
			.put("id", this.id)
			.put("apiary_id", this.apiary.getId())
			.put("number_of_suppers", this.suppers)
			.put("notes", this.notes);
		
		return json;
	}

	@Override
	public Object translate(JSONObject json) {
		Hive hive = new Hive();
		
		try {
			// TODO Inform Petar to put a name field in the database ASAP
			hive.id = "HID" + json.getInt("id");
			hive.name = "Hive"; 
			hive.notes = json.getString("notes");
			hive.suppers = json.getInt("number_of_suppers");
			hive.moveToApiary(
					LocalStore.findApiaryById(
							"AID" + json.getInt("apiary_id")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return hive;
	}
	
}
