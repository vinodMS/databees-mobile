package nl.isld.databees;

import org.json.JSONObject;

public interface JsonParserCallback {
	
	public void onParsed(JSONObject object);

}
