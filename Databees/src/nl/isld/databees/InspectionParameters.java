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
