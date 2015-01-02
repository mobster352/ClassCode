package com.cs490;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.Arrays;
import com.google.gson.Gson;

public class FormFacade{

	private static FormFacade singleton;
	private FormDataAccess dao;
	
	//Data Access
	private FormFacade() throws NamingException, SQLException{
		this.dao = FormDataAccess.getInstance();
	}
	public static FormFacade getInstance()throws NamingException, SQLException{
		if(singleton == null)
			singleton = new FormFacade();
		
		return singleton;
	}

	/**Start Course Repeat**/
	public CourseRepeatForm[] getRepeatForms() throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM courserepeat");
		ResultSet rs = pstmt.executeQuery();
		
		CourseRepeatForm[] formArray = new CourseRepeatForm[100];
		int index=0;
		while(rs.next()){	
			String repeat = rs.getString("repeat");
			String termtaken = rs.getString("termTaken");
			String grade = rs.getString("grade");
			String termrepeat = rs.getString("termRepeat");
			String studentid = rs.getString("studentId");
			
			CourseRepeatForm formObject = new CourseRepeatForm(repeat,termtaken,grade,termrepeat);
			
			formArray[index] = formObject;
			index++;
		}
		if(index > 0){
			formArray = Arrays.copyOf(formArray,index);
			return formArray;
		}
		else{
			return null;
		}
	}
	
	public CourseRepeatForm[] getTermRepeatFormById(int theId)throws SQLException, ClassNotFoundException{
		Connection con = dao.getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM courserepeat WHERE id = ?");
		stmt.setInt(1,theId);
		ResultSet rs = stmt.executeQuery();
		
		CourseRepeatForm[] formArray = new CourseRepeatForm[100];
		int index = 0;
		
		while(rs.next()){
			String repeat = rs.getString("repeat");
			String termTaken = rs.getString("termTaken");
			String grade = rs.getString("grade");
			String termRepeat = rs.getString("termRepeat");
			
			
			CourseRepeatForm formObject = new CourseRepeatForm(repeat,termTaken,grade,termRepeat);
			
			formArray[index] = formObject;
			index++;
		}
		if( index > 0){
			formArray = Arrays.copyOf(formArray,index);
			return formArray;
		}
		else{
			return formArray;
		}
	}
	
	public String createTermToRepeatForm(CourseRepeatForm theFormToCreate) throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
	
		String courseToRepeat = theFormToCreate.getCourseToRepeat();
		String termTaken = theFormToCreate.getTermTaken();
		String gradeEarned = theFormToCreate.getGradeEarned();
		String termToRepeat = theFormToCreate.getTermToRepeat();
		
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO courserepeat(courseToRepeat,termTaken,gradeEarned,termToRepeat) VALUES (?,?,?,?)");
		
		pstmt.setString(1,courseToRepeat);
		pstmt.setString(2,termTaken);
		pstmt.setString(3,gradeEarned);
		pstmt.setString(4,termToRepeat);
		int res = pstmt.executeUpdate();
		String answer = "failed";
		
		CourseRepeatForm[] userArray = new CourseRepeatForm[100];
		int index = 0;
					
		if(res == 1){
			answer = "passed";
			return answer;
		}
		else{
			return answer;
		}
	}
	/**End Course Repeat **/
	
	/**Start Course Sub**/
	public CourseSubForm[] getSubForms() throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM coursesub");
		ResultSet rs = pstmt.executeQuery();
		
		CourseSubForm[] formArray = new CourseSubForm[100];
		int index=0;
		while(rs.next()){	
			String sourceCourse1 = rs.getString("sourceCourse1");
			String replacementCourse1 = rs.getString("replacementCourse1"); 
			String sourceCourse2 = rs.getString("sourceCourse2");
			String replacementCourse2 = rs.getString("replacementCourse2"); 
			String sourceCourse3 = rs.getString("sourceCourse3");
			String replacementCourse3 = rs.getString("replacementCourse3"); 			
			
			CourseSubForm formObject = new CourseSubForm(sourceCourse1,replacementCourse1,sourceCourse2,replacementCourse2,sourceCourse3,replacementCourse3);
			
			formArray[index] = formObject;
			index++;
		}
		if(index > 0){
			formArray = Arrays.copyOf(formArray,index);
			return formArray;
		}
		else{
			return null;
		}
	}
	
	public CourseSubForm[] getSubFormById(int theId)throws SQLException, ClassNotFoundException{
		Connection con = dao.getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM coursesub WHERE id = ?");
		stmt.setInt(1,theId);
		ResultSet rs = stmt.executeQuery();
		
		CourseSubForm[] formArray = new CourseSubForm[100];
		int index = 0;
		
		while(rs.next()){
			String sourceCourse1 = rs.getString("sourceCourse1");
			String replacementCourse1 = rs.getString("replacementCourse1"); 
			String sourceCourse2 = rs.getString("sourceCourse2");
			String replacementCourse2 = rs.getString("replacementCourse2"); 
			String sourceCourse3 = rs.getString("sourceCourse3");
			String replacementCourse3 = rs.getString("replacementCourse3"); 				
			
			CourseSubForm formObject = new CourseSubForm(sourceCourse1,replacementCourse1,sourceCourse2,replacementCourse2,sourceCourse3,replacementCourse3);
			
			formArray[index] = formObject;
			index++;
		}
		if( index > 0){
			formArray = Arrays.copyOf(formArray,index);
			return formArray;
		}
		else{
			return formArray;
		}
	}
	
	public String createSubForm(CourseSubForm theFormToCreate) throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
	
		String sourceCourse1 = theFormToCreate.getSubstituteFor1();
		String replacementCourse1 = theFormToCreate.getRequirement1(); 
		String sourceCourse2 = theFormToCreate.getSubstituteFor2();
		String replacementCourse2 = theFormToCreate.getRequirement2(); 
		String sourceCourse3 = theFormToCreate.getSubstituteFor3();
		String replacementCourse3 = theFormToCreate.getRequirement3(); 
		
		
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO coursesub(sourceCourse1,replacementCourse1,sourceCourse2,replacementCourse2,sourceCourse3,replacementCourse3) VALUES (?,?,?,?,?,?)");
		
		pstmt.setString(1,sourceCourse1);
		pstmt.setString(2,replacementCourse1);
		pstmt.setString(3,sourceCourse2);
		pstmt.setString(4,replacementCourse2);
		pstmt.setString(3,sourceCourse3);
		pstmt.setString(4,replacementCourse3);
		
		int res = pstmt.executeUpdate();
		String answer = "failed";
		
		CourseSubForm[] userArray = new CourseSubForm[100];
		int index = 0;
					
		if(res == 1){
			answer = "passed";
			return answer;
		}
		else{
			return answer;
		}
	}
	/**End Sub Form**/
	
	/**Start Transcript**/
	public TranscriptForm[] getTranscriptForms() throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM transcriptform");
		ResultSet rs = pstmt.executeQuery();
		
		TranscriptForm[] formArray = new TranscriptForm[100];
		int index=0;
		while(rs.next()){	
			
			String firstTermAttended = rs.getString("firstTermAttended");
			String firstYearAttended = rs.getString("firstYearAttended"); 
			String lastTermAttended = rs.getString("lastTermAttended");
			String lastYearAttended = rs.getString("lastYearAttended");
			String toBeCompleted = rs.getString("toBeCompleted");
			
			String numTrans1 = rs.getString("numTrans1");
			String name1 = rs.getString("name1");
			
			String numTrans2 = rs.getString("numTrans2");
			String name2 = rs.getString("name2");
			
			String numTrans3 = rs.getString("numTrans3");
			String name3 = rs.getString("name3");	
	
			TranscriptForm formObject = new TranscriptForm(firstTermAttended,lastTermAttended,toBeCompleted,firstYearAttended,lastYearAttended,numTrans1,name1,numTrans2,name2,numTrans3,name3);
			
			formArray[index] = formObject;
			index++;
		}
		if(index > 0){
			formArray = Arrays.copyOf(formArray,index);
			return formArray;
		}
		else{
			return null;
		}
	}
	
	public TranscriptForm[] getTranscriptFormById(int theId)throws SQLException, ClassNotFoundException{
		Connection con = dao.getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM transcriptform WHERE id = ?");
		stmt.setInt(1,theId);
		ResultSet rs = stmt.executeQuery();
		
		TranscriptForm[] formArray = new TranscriptForm[100];
		int index = 0;
		
		while(rs.next()){
			
			String firstTermAttended = rs.getString("firstTermAttended");
			String firstYearAttended = rs.getString("firstYearAttended"); 
			String lastTermAttended = rs.getString("lastTermAttended");
			String lastYearAttended = rs.getString("lastYearAttended");
			String toBeCompleted = rs.getString("toBeCompleted");
			
			String numTrans1 = rs.getString("numTrans1");
			String name1 = rs.getString("name1");
			
			String numTrans2 = rs.getString("numTrans2");
			String name2 = rs.getString("name2");
			
			String numTrans3 = rs.getString("numTrans3");
			String name3 = rs.getString("name3");
			
			TranscriptForm formObject = new TranscriptForm(firstTermAttended,lastTermAttended,toBeCompleted,firstYearAttended,lastYearAttended,numTrans1,name1,numTrans2,name2,numTrans3,name3);
			
			formArray[index] = formObject;
			index++;
			
		}
		if( index > 0){
			formArray = Arrays.copyOf(formArray,index);
			return formArray;
		}
		else{
			return formArray;
		}
	}
	
	public String createTranscriptForm(TranscriptForm theFormToCreate) throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
	
		
			String firstTermAttended = theFormToCreate.getFirstTerm();
			String firstYearAttended = theFormToCreate.getYearFirst(); 
			String lastTermAttended = theFormToCreate.getLastTerm();
			String lastYearAttended = theFormToCreate.getYearLast();
			String toBeCompleted = theFormToCreate.getToBeCompleted();
			
			String numTrans1 = theFormToCreate.getNumTrans1();
			String name1 = theFormToCreate.getName1();
			
			String numTrans2 = theFormToCreate.getNumTrans2();
			String name2 = theFormToCreate.getName2();
			
			String numTrans3 = theFormToCreate.getNumTrans3();
			String name3 = theFormToCreate.getName3();
	
			
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO transcriptform(firstTermAttended,lastTermAttended,toBeCompleted,firstYearAttended,lastYearAttended,numTrans1,name1,numTrans2,name2,numTrans3,name3) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		
		pstmt.setString(1,firstTermAttended);
		pstmt.setString(2,lastTermAttended);
		pstmt.setString(3,toBeCompleted);
		pstmt.setString(4,firstYearAttended);
		pstmt.setString(5,lastYearAttended);
		pstmt.setString(6,numTrans1);
		pstmt.setString(7,name1);
		pstmt.setString(8,numTrans2);
		pstmt.setString(9,name2);
		pstmt.setString(10,numTrans3);
		pstmt.setString(11,name3);
		
		
		
		
		
		int res = pstmt.executeUpdate();
		String answer = "failed";
		
		TranscriptForm[] userArray = new TranscriptForm[100];
		int index = 0;
					
		if(res == 1){
			answer = "passed";
			return answer;
		}
		else{
			return answer;
		}
	}
	/**End Transcript **/
}
