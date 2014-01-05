package nl.isld.databees;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Hive implements Parcelable {

	public static final String PARCEL_KEY	=	"PARCELABLE_HIVE";
	
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
	
	public Hive(Parcel source) {
		this.id			= source.readString();
		this.name		= source.readString();
		this.apiary		= LocalStore.findApiaryById(source.readString());
		this.colony		= source.readParcelable(null);
	}
	
	public static final Parcelable.Creator<Hive> CREATOR =
			new Parcelable.Creator<Hive>() {

				@Override
				public Hive createFromParcel(Parcel source) {
					return new Hive(source);
				}

				@Override
				public Hive[] newArray(int size) {
					return new Hive[size];
				}
		
			};
			
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		if(apiary != null){	
			dest.writeString(apiary.getId());
		} else {
			dest.writeString(new String());
		}
		dest.writeParcelable(colony, flags);
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
		
		while((colony = colony.getParent()) != null) {
			inspections.addAll(colony.getInspections());
		}
		
		return inspections;
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
	
}
