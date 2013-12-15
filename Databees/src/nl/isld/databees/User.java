package nl.isld.databees;

import org.apache.http.auth.Credentials;

public class User {
	
	public int ID;
	public String name, email;
	public Credentials credentials;
	
	public User(int ID, String name, Credentials credential, String email) {
		
	}
	
	public int getID() {
		return ID ;
	}
	
	public String getName() {
		return name ;
	}
	
	public void getCredentials() {
		
	}
	
	public String getEmail() {
		return email ;
	}	
}
