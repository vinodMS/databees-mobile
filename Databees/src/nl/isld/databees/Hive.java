package nl.isld.databees;

public class Hive {

	private Apiary		apiary;
	private String		id;
	private String		name;
	
	Hive() {
		this.id			= null;
		this.apiary		= null;
		this.name		= null;
	}
	
	Hive(Apiary apiary, String id, String name) {
		this.id			= id;
		this.apiary		= apiary;
		this.name		= name;
	}
	
	// GETTERS
	public Apiary getApiary() {
		return apiary;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	// SETTERS
	public void moveToApiary(Apiary apiary) {
		this.apiary = apiary;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
