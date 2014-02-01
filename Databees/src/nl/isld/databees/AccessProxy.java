package nl.isld.databees;

import org.json.JSONException;
import org.json.JSONObject;

public interface AccessProxy {

	public JSONObject translate() throws JSONException;
	public Object translate(JSONObject json);
}
