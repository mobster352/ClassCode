package com.cs490;
public class User{
	//Attributes 
	private int id= 0;
	private String userName = "";
	private String userPassword = "";

	//default constructor
	public User(){
		id = 0;
		userName = "No name yet";
		userPassword = "No password yet";
	}
	
	public User(int aId){
		this.id = aId;
	}
	
	public User(int aId, String aName, String aPassword){
		id = aId;
		userName = aName;
		userPassword = aPassword;
	}

	public User(String aName, String aPassword){
		this.id = 0;
		this.userName = aName;		
		this.userPassword = aPassword;
	}
	public int getId(){
		return id;
	}
	public String getUserName(){
		return userName;
	}
	public String getUserPassword(){
		return userPassword;
	}	
	public void setId(int aId){
		this.id = aId;

	}	
	public void setUserName(String aName){
		this.userName = aName;
	}
	public void setUserPassword(String aPassword){
		this.userPassword = aPassword;
	}
	
	//Also include a toString that creates a String representation	in the form id: name (category)	
	public String toString(){
		String text = "";
		text += "id: "+this.id+" "+this.userName+" , "+this.userPassword+" ";
		return text;
	}
}//end class
