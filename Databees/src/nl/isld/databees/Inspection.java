package nl.isld.databees;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Inspection extends Object implements AccessProxy {
	
	private String					id;
	private Date					date;
	private String					notes;
	private InspectionParameters	parameters;
	private Colony					colony;
	
	public Inspection() {
		this.id			= LocalStore.generateInspectionId();
		this.date		= new Date();
		this.notes		= new String();
		this.parameters	= null;
		this.colony		= null;
	}
	
	public Inspection(Colony colony, Date date, String notes, InspectionParameters parameters) {
		this.id			= LocalStore.generateInspectionId();
		this.date		= date;
		this.notes		= notes;
		this.parameters	= parameters;
		this.colony		= colony;
		
		this.colony.addInspection(this);
	}

	public String getId() {
		return id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public InspectionParameters getParameters() {
		return parameters;
	}
	
	public Colony getColony() {
		return colony;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setParameters(InspectionParameters parameters) {
		this.parameters = parameters;
	}
	
	public void setColony(Colony colony) {
		this.colony = colony;
	}

	@Override
	public JSONObject translate() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object translate(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

}
