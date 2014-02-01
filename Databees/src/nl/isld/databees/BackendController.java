package nl.isld.databees;

import nl.isld.databees.AsyncJsonMessage.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

public class BackendController {
	
	private static String	HTTPS_QUERY_REGISTER	= "https://api.databees.isld.nl/register";
	private static String	REGISTER_CONTENT		= "{\"email\": \"%email\",\"password\": \"%password\",\"name\": \"%name\"}";
	private static String	HTTPS_QUERY_LOGIN		= "https://api.databees.isld.nl/login/%email/%password";
	private static String 	HTTPS_QUERY_STORE		= "https://api.databees.isld.nl/dbexec/%db_table";
	private static String	HTTPS_QUERY_RETRIEVE	= "https://api.databees.isld.nl/dbquery/%db_table";
	
	public static	String API_KEY = new String();
	public static	String USER_ID = "25";

	public static void register(String name, String email, String password,
			ResponseRecipient recipient) {
		try {
			new AsyncJsonMessage().setContent(new JSONObject(
					REGISTER_CONTENT.replace("%email", email)
									.replace("%password", password)
									.replace("%name", name)))
					.viaHttpRequest(HTTPS_QUERY_REGISTER, recipient, true, HttpMethod.POST);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void login(String email, String password,
			ResponseRecipient recipient) {
		new AsyncJsonMessage().viaHttpRequest(
				HTTPS_QUERY_LOGIN.replace("%email", email)
								 .replace("%password", password),
				recipient, true, HttpMethod.GET);
	}
	
	public static void logout() {
		API_KEY = new String();
	}
	
	public static void store(String dbTable, JSONObject json,
			ResponseRecipient recipient) {
		new AsyncJsonMessage()
			.setContent(json)
			.viaHttpRequest(
					HTTPS_QUERY_STORE.replace("%db_table", dbTable),
					recipient, true, HttpMethod.POST);
	}

	public static void retrieve(String dbTable, int key,
			ResponseRecipient recipient) {
	}
}
