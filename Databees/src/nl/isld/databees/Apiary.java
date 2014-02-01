package nl.isld.databees;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Apiary extends Object implements AccessProxy {
	
	private String		id;
	private String		name;
	private LatLng		location;
	private String		notes;
	private List<Hive>	hives;
	
	Apiary() {
		this.id			= LocalStore.generateApiaryId();
		this.name		= new String();
		this.location	= null;
		this.notes		= new String();
		this.hives		= new ArrayList<Hive>();
	}
	
	Apiary(String name, LatLng location, String notes) {
		this.id			= LocalStore.generateApiaryId();
		this.name		= name;
		this.location	= location;
		this.notes		= notes;
		this.hives		= new ArrayList<Hive>();
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public LatLng getLocation() {
		return location;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public Hive getHive(int position) {
		return hives.get(position);
	}
	
	public List<Hive> getHives() {
		return hives;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLocation(LatLng location) {
		this.location = location;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void addHive(Hive hive) {
		hive.setApiary(this);
		hives.add(hive);
	}
	
	public void removeHive(Hive hive) {
		hives.remove(hive);
	}

	@Override
	public JSONObject translate() throws JSONException {
		JSONObject json = new JSONObject();
		
		json
			.put("id", this.id)
			.put("user_id", BackendController.USER_ID)
			.put("name", this.name)
			.put("geo_lat", this.location.latitude)
			.put("geo_long", this.location.longitude)
			.put("notes", this.notes);
		
		return json;
	}

	@Override
	public Object translate(JSONObject json) {
		Apiary apiary = new Apiary();
		
		try {
			apiary.id = "AID" + json.getInt("id");
			apiary.name = json.getString("name");
			apiary.location = new LatLng(
					json.getDouble("geo_lat"),
					json.getDouble("geo_long"));
			apiary.notes = json.getString("notes");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return apiary;
	}

}
