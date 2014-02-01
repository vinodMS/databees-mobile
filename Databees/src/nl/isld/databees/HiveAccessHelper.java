package nl.isld.databees;

public class HiveAccessHelper implements DataAccess {
	
	private ResponseRecipient recipient;
	
	protected HiveAccessHelper() {
		this.recipient = null;
	}

	public void setRecipient(ResponseRecipient recipient) {
		this.recipient = recipient;
	}
	
	public ResponseRecipient getRecipient() {
		return this.recipient;
	}
	
	@Override
	public int store(Object object) {
		Hive hive = (Hive) object;
		
		return 0;
	}

	@Override
	public void retrieve(String id) {
		int key = convertId(id);
		BackendController.retrieve("hive" , key, recipient);
	}
	
	@Override
	public int convertId(String id) {
		return Integer.parseInt(id.substring(3));
	}
	
	static HiveAccessHelper instance = null;
	
	public static HiveAccessHelper getInstance() {
		
		if(instance == null) {
			return instance = new HiveAccessHelper();
		} else {
			return instance;
		}
	}

}
