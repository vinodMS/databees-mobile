package nl.isld.databees;

public interface DataAccess {

	public int store(Object object);
	public void retrieve(String id);
	public int convertId(String id);
}
