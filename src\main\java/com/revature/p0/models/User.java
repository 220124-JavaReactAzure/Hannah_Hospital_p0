package com.revature.p0.models;

public class User {
	
	private String type;
	private String firstName;
	private String lastName;
	
	
	public User(String type, String firstName, String lastName) {
		super();
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public String getType() {
		return type;
	}


	public String getFirstName() {
		return firstName;
	}



	public String getLastName() {
		return lastName;
	}


	

	
	
}
