package nl.isld.databees;

public class Hive {

	public int ID, totalFrames;
	public String name, photoUri;
	
	public Hive (int ID, String name, int totalFrames, String photoUri) {
		
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTotalFrames() {
		return totalFrames;
	}
	
	public String getPhotoUri() {
		return photoUri;
	}
}
