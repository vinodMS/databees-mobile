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

public class Task {

	public static final String PARCEL_KEY	=	"PARCELABLE_apiary";
	
	private String		id;
	private String		title;
	private String		desc;
	private Date		taskDate;
	
	Task() {
		this.id			= LocalStore.generateTaskId();
		this.title		= new String();
		this.desc		= new String();
		this.taskDate   = new Date();
	}
	
	Task(String title, String desc, Date taskDate) {
		this.id			= LocalStore.generateTaskId();
		this.title		= title;
		this.desc		= desc;
		this.taskDate	= taskDate;
	}

	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	
	public String getDesc() {
		return desc;
	}
	
	public Date getTaskDate() {
		return taskDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public void setTaskDate(String trim) {
		// TODO Auto-generated method stub
		
	}
	
}
