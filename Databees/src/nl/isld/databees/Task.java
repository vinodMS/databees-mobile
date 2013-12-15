package nl.isld.databees;

import java.sql.Date;

public class Task {
	
	public int ID;
	public String desc;
	public Date deadLine, alarmDate;

	public Task(int ID, String desc, Date deadLine, boolean alarmOn, Date alarmDate) {
		
	}
		
	public int getID() {
		return ID;	
	}
	
	public String getDescription() {
		return desc ;
	}
	
	public Date getDeadLine() {
		return deadLine ;
	}
	
	public boolean isAlarmOn() {
		return false;
	}
	
	public Date getAlarmDate() {
		return alarmDate;
	}
}
