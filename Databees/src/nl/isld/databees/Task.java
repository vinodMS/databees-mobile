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
