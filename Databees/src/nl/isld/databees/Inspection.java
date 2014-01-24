/*
	Databees a beekeeping organizer app.
    Copyright (C) 2014 NBV (Nederlandse Bijenhouders Vereniging)
    http://www.bijenhouders.nl/

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package nl.isld.databees;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Inspection implements Parcelable {
	
	public static final String PARCEL_KEY	=	"PARCELABLE_INSPECTION";
	
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
	
	public Inspection(Parcel source) {
		this.id			= source.readString();
		this.date		= new Date(source.readLong());
		this.notes		= source.readString();
		this.parameters = source.readParcelable(null);
		this.colony		= null;
	}
	
	public static final Parcelable.Creator<Inspection> CREATOR =
			new Parcelable.Creator<Inspection>() {

				@Override
				public Inspection createFromParcel(Parcel source) {
					return new Inspection(source);
				}

				@Override
				public Inspection[] newArray(int size) {
					return new Inspection[size];
				}
		
			};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeLong(date.getTime());
		dest.writeString(notes);
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

}
