package nl.isld.databees;

import org.json.JSONObject;

public class BackendCommunicator {
	
	private static String	HTTPS_QUERY_REGISTER	= "https://api.databees.isld.nl/register";
	private static String	HTTPS_QUERY_LOGIN		= "https://api.databees.isld.nl/login/%email/%password";
	private static String 	HTTPS_QUERY_STORE		= "https://api.databees.isld.nl/dbexec/%db_table";
	private static String	HTTPS_QUERY_RETRIEVE	= "https://api.databees.isld.nl/dbquery/%db_table";
	
	public static  String API_KEY = new String();

	public static void register(String name, String email, String password,
			ResponseRecipient recipient) {
		
	}
	
	public static void login(String email, String password,
			ResponseRecipient recipient) {
		
	}
	
	public static void logout() {
		API_KEY = new String();
	}
	
	public static void store(String dbTable, JSONObject json,
			ResponseRecipient recipient) {
		
	}

	public static void retrieve(String dbTable, String id,
			ResponseRecipient recipient) {
		
	}
}
