/*
	Databees a beekeeping organizer app.
    Copyright (C) 2014 NBV (Nederlandse Bijenhouders Vereniging)
    http://www.bijenhouders.nl/

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
