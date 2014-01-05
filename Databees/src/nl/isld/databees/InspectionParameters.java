package nl.isld.databees;

import android.os.Parcel;
import android.os.Parcelable;

public class InspectionParameters implements Parcelable {
	
	public static final String PARCEL_KEY	=	"PARCELABLE_INSPECTION_PARAMETERS";

	public enum Temper { Undefined, Mild, Angry, Tantrum } 
	
	public Temper		temper;
	public boolean		polen;
	public boolean		eggs;
	
	public InspectionParameters(Parcel source) {
		this.temper			= Temper.valueOf(source.readString());
		this.polen			= (Boolean) source.readValue(null);
		this.eggs			= (Boolean) source.readValue(null);
	}
	
	public static final Parcelable.Creator<InspectionParameters> CREATOR =
			new Parcelable.Creator<InspectionParameters>() {

				@Override
				public InspectionParameters createFromParcel(Parcel source) {
					return new InspectionParameters(source);
				}

				@Override
				public InspectionParameters[] newArray(int size) {
					return new InspectionParameters[size];
				}
			};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(temper.toString());
		dest.writeValue(polen);
		dest.writeValue(eggs);
	}

}
