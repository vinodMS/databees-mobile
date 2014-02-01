package nl.isld.databees;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Colony extends Object implements AccessProxy {
	
	private String				id;
	private Hive				hive;
	private Colony				parent;
	private List<Inspection>	inspections;
	
	public Colony() {
		this.id				= null;
		this.hive			= null;
		this.parent			= null;
		this.inspections	= new ArrayList<Inspection>();
	}
	
	public Colony(Hive hive) {
		this.id				= LocalStore.generateColonyId();
		this.hive			= hive;
		this.parent			= null;
		this.inspections	= new ArrayList<Inspection>();
		
		hive.setColony(this);
	}
	
	public String getId() {
		return id;
	}
	
	public Hive getHive() {
		return hive;
	}
	
	public Colony getParent() {
		return parent;
	}
	
	public Inspection getIspection(int index) {
		return inspections.get(index);
	}
	
	public List<Inspection> getInspections() {
		return inspections;
	}
	
	public void setHive(Hive hive) {
		this.hive = hive;
	}
	
	public void addInspection(Inspection inspection) {
		inspection.setColony(this);
		inspections.add(inspection);
	}
	
	public Colony swarm() {
		Colony colony = new Colony(hive);
		colony.parent = this;
		return colony;
	}

	@Override
	public JSONObject translate() throws JSONException {
		JSONObject json = new JSONObject();
		
		json
			.put("id", id)
			.put("hive_id", hive.getId())
			.put("parent_id", parent.getId());
		
		return json;
	}

	@Override
	public Object translate(JSONObject json) {
		Colony colony = new Colony();
		
		try {
			colony.id = "CID" + json.getInt("id");
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return colony;
	}

}
