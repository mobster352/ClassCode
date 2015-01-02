package com.cs490;

public class CourseSubForm extends Form{
	private int formId = 0;
	private String substituteFor1 = "";
	private String requirement1 = "";
	private String substituteFor2 = "";
	private String requirement2 = "";
	private String substituteFor3 = "";
	private String requirement3 = "";

	public CourseSubForm(){
		formId = super.getFormId();
		this.substituteFor1 = "Not yet";
		this.requirement1 = "Not yet";
		this.substituteFor2 = "Not yet";
		this.requirement2 = "Not yet";
		this.substituteFor3 = "Not yet";
		this.requirement3 = "Not yet";
	}
	
	public CourseSubForm(String aSub1, String aReq1,String aSub2, String aReq2,String aSub3, String aReq3){
		this.formId = super.getFormId();
		this.substituteFor1 = aSub1;
		this.requirement1 = aReq1;
		this.substituteFor2 = aSub2;
		this.requirement2 = aReq2;
		this.substituteFor3 = aSub3;
		this.requirement3 = aReq3;
		
	}
	
	//Gets
	public String getSubstituteFor1(){
		return this.substituteFor1;
	}
	public String getRequirement1(){
		return this.requirement1;
	}
	public String getSubstituteFor2(){
		return this.substituteFor2;
	}
	public String getRequirement2(){
		return this.requirement2;
	}
	public String getSubstituteFor3(){
		return this.substituteFor3;
	}
	public String getRequirement3(){
		return this.requirement3;
	}
	
	
	//Sets
	public void setSubstituteFor1(String aSub1){
		this.substituteFor1 = aSub1;
	}
	public void setRequirement1(String aReq1){
		this.requirement1 = aReq1;
	}
	
	public void setSubstituteFor2(String aSub2){
		this.substituteFor2 = aSub2;
	}
	public void setRequirement2(String aReq2){
		this.requirement2 = aReq2;
	}
	
	public void setSubstituteFor3(String aSub3){
		this.substituteFor3 = aSub3;
	}
	public void setRequirement3(String aReq3){
		this.requirement3 = aReq3;
	}
	
	
	//Also include a toString that creates a String representation
	public String toString(){
		String text = "";
		text += "form Id: "+this.formId+" "+this.substituteFor1+" "+this.requirement1+" "+this.substituteFor2+" "+this.requirement2+" "+this.substituteFor3+" "+this.requirement3;
		return text;
	}

}//End Class
