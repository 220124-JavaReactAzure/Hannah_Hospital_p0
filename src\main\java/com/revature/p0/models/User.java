package com.revature.p0.models;

public class User {

	private String type;
	private int ID;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	
	public User() {
		
	}

	public User(String type, String firstName, String lastName) {
		super();
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	

	public User(String type, int iD, String firstName, String lastName, String userName, String password) {
		super();
		this.type = type;
		ID = iD;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
	
	

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
