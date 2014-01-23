package nl.isld.databees;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Colony implements Parcelable {
	
	public static final String PARCEL_KEY	=	"PARCELABLE_COLONY";
	
	private String				id;
	private Hive				hive;
	private Colony				parent;
	private List<Inspection>	inspections;
	
	public Colony(Hive hive) {
		this.id				= LocalStore.generateColonyId();
		this.hive			= hive;
		this.parent			= null;
		this.inspections	= new ArrayList<Inspection>();
		
		hive.setColony(this);
	}
	
	public Colony(Parcel source) {
		this.id				= source.readString();
		this.hive			= LocalStore.findHiveById(source.readString());
		this.parent			= source.readParcelable(null);
		source.readList(this.inspections, null);
	}
	
	public static final Parcelable.Creator<Colony> CREATOR = 
			new Parcelable.Creator<Colony>() {

				@Override
				public Colony createFromParcel(Parcel source) {
					return new Colony(source);
				}

				@Override
				public Colony[] newArray(int size) {
					return new Colony[size];
				}
		
			};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(hive.getId());
		dest.writeParcelable(parent, flags);
		dest.writeList(inspections);
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

}
