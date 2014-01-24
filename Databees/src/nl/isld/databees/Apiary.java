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

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class Apiary {

	public static final String PARCEL_KEY	=	"PARCELABLE_APIARY";
	
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

}
