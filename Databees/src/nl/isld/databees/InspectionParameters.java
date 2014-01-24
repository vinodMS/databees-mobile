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

public class InspectionParameters {

	public enum Temper { Undefined, Mild, Angry, Tantrum } 
	
	public Temper		temper;
	public boolean		polen;
	public boolean		eggs;
	public boolean		queenSeen;
	public boolean		queenMarked;
	public String		queenOrigin;
	public String		disease;
	public String		other;
	
	public InspectionParameters() {
		this.temper			= Temper.Undefined;
		this.polen			= false;
		this.eggs			= false;
		this.queenSeen		= false;
		this.queenMarked	= false;
		this.queenOrigin	= new String();
		this.disease		= new String();
		this.other			= new String();
	}

}
