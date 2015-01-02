package com.cs490;

public class TranscriptForm extends Form{
	
	private int formId = 0;
	private String firstTerm="";
	private String lastTerm = "";
	private String toBeCompleted = "";
	private String yearFirst = "";
	private String yearLast = "";
	private String numTrans1 = "";
	private String numTrans2 = "";
	private String numTrans3 ="";
	private String name1="";
	private String name2="";
	private String name3="";
	
	
	//Default Constructor
	public TranscriptForm(){
		this.formId = super.getFormId();
		this.firstTerm="None yet";
		this.lastTerm = "None yet";
		this.toBeCompleted = "None yet";
		this.yearFirst = "None yet";
		this.yearLast = "None yet";
		this.numTrans1 = "None yet";
		this.numTrans2 = "None yet";
		this.numTrans3 ="None yet";
		this.name1="None yet";
		this.name2="None yet";
		this.name3="None yet";
	}
	
	//Overloaded Constructor
	public TranscriptForm(String aFirstTerm,String aLastTerm,String aCompleted,String aFirstYear,String aLastYear, String aNum1,String aName1,String aNum2,String aName2,String aNum3,String aName3){
		this.formId = super.getFormId();
		this.firstTerm= aFirstTerm;
		this.lastTerm = aLastTerm;
		this.toBeCompleted = aCompleted;
		this.yearFirst = aFirstYear;
		this.yearLast = aLastYear;
		this.numTrans1 = aNum1;
		this.numTrans2 = aNum2;
		this.numTrans3 = aNum3;
		this.name1 = aName1;
		this.name2 = aName2;
		this.name3 = aName3;
	}	
	
	//Gets
	public String getFirstTerm(){
		return this.firstTerm;
	}
	public String getLastTerm(){
		return this.lastTerm;
	}
	public String getToBeCompleted(){
		return this.toBeCompleted;
	}
	public String getYearFirst(){
		return this.yearFirst;
	}
	public String getYearLast(){
		return this.yearLast;
	}
	public String getNumTrans1(){
		return this.numTrans1;
	}
	public String getNumTrans2(){
		return this.numTrans2;
	}
	public String getNumTrans3(){
		return this.numTrans3;
	}
	public String getName1(){
		return this.name1;
	}
	public String getName2(){
		return this.name2;
	}
	public String getName3(){
		return this.name3;
	}
	
	
	
	//sets
	public void setFirstTerm(String aFirstTerm){
		this.firstTerm=aFirstTerm;
	}
	public void setLastTerm(String aLastTerm){
		this.lastTerm = aLastTerm;
	}
	public void setToBeCompleted(String aCompleted){
		this.toBeCompleted = aCompleted;
	}
	public void setYearFirst(String aFirstYear){
		this.yearFirst = aFirstYear;
	}
	public void setYearLast(String aLastYear){
		this.yearLast = aLastYear;
	}
	public void setNumTrans1(String aNum1){
		this.numTrans1 = aNum1;
	}
	public void setNumTrans2(String aNum2){
		this.numTrans2 = aNum2;
	}
	public void setNumTrans3(String aNum3){
		this.numTrans3 = aNum3;
	}
	public void setName1(String aName1){
		this.name1 = aName1;
	}
	public void setName2(String aName2){
		this.name2 = aName2;
	}
	public void setName3(String aName3){
		this.name3 = aName3;
	}
	
	
	
	
	//Also include a toString that creates a String representation
	public String toString(){
		String text = "";
		text += "form Id: "+this.formId+" "+this.firstTerm+" "+this.lastTerm+" "+this.toBeCompleted+" "+this.yearFirst+" "+this.yearLast+" "+this.numTrans1+" "+this.name1+" "+this.numTrans2+" "+this.name2+" "+this.numTrans3+" "+this.name3;
		return text;
	}
	
	
}
