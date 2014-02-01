package nl.isld.databees;

import org.json.JSONObject;

public interface ResponseRecipient {
	
	public void onResponseReceived(JSONObject json);

}
