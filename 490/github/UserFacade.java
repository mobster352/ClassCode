package com.cs490;
import java.sql.Connection;
import javax.naming.NamingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserFacade{
	//attributes
	private static UserFacade singleton;
	private UserDataAccess dao;
	
	//constructor
	private UserFacade() throws NamingException, SQLException{
		this.dao = UserDataAccess.getInstance();
	}

	public static UserFacade getInstance()throws NamingException, SQLException{
		if(singleton == null)
			singleton = new UserFacade();
		
		return singleton;
	}
	
	public int deleteUser(int theId)throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("DELETE from user where id=?");
		pstmt.setInt(1,theId);
		int res = pstmt.executeUpdate();
		if(res != 1){
			return 0;
		}
		else{
			return res;
		}
	}
	
	public User[] updateUser(int id, User theUserToUpdate)throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		String userNameUpdate = theUserToUpdate.getUserName();
		String userPasswordUpdate = theUserToUpdate.getUserPassword();
		
		System.out.println("facadeid:"+id);
		System.out.println("facadeN:"+userNameUpdate);
		System.out.println("facadeP:"+userPasswordUpdate);
			
			
		String oldName=null;
		String oldPassword =null;
		
		PreparedStatement stmt = con.prepareStatement("SELECT userName, userPassword FROM user WHERE id=?");
		stmt.setInt(1,id);
		ResultSet rs = stmt.executeQuery();
		int index = 0;
		User[] userArray = new User[100];
		
		
		while(rs.next()){
			
			oldName = rs.getString("userName");
			oldPassword = rs.getString("userPassword");
			
		}
		System.out.println("oldN:"+oldName);
		System.out.println("oldP:"+oldPassword);
		
		if(userNameUpdate.isEmpty() && userPasswordUpdate.isEmpty()){}
		if(!userNameUpdate.isEmpty() && userPasswordUpdate.isEmpty())
		{
			PreparedStatement pstmt = con.prepareStatement("UPDATE user SET userName=?, userPassword=? WHERE id=?");
			pstmt.setString(1,userNameUpdate);
			pstmt.setString(2,oldPassword);
			pstmt.setInt(3,id);
			pstmt.executeUpdate();
		}
		if(userNameUpdate.isEmpty() && !userPasswordUpdate.isEmpty())
		{
			PreparedStatement pstmt = con.prepareStatement("UPDATE user SET userName=?, userPassword=? WHERE id=?");
			pstmt.setString(1,oldName);
			pstmt.setString(2,userPasswordUpdate);
			pstmt.setInt(3,id);
			pstmt.executeUpdate();
		}
		if(!userNameUpdate.isEmpty() && !userPasswordUpdate.isEmpty())
		{
			PreparedStatement pstmt = con.prepareStatement("UPDATE user SET userName=?, userPassword=? WHERE id=?");
			pstmt.setString(1,userNameUpdate);
			pstmt.setString(2,userPasswordUpdate);
			pstmt.setInt(3,id);
			pstmt.executeUpdate();
		}
		
		
		User userObject = new User(id,userNameUpdate,userPasswordUpdate);
		userArray[index] = userObject;
		index++;
		
		if(index > 0){
			userArray = Arrays.copyOf(userArray,index);
			return userArray;
		}
		else{
			return null;
		}
		
	}

	public User[] createUser(User theUserToAdd)throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		String userName = theUserToAdd.getUserName();
		String userPassword = theUserToAdd.getUserPassword();
		
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (userName,userPassword) VALUES(?,?)");
		pstmt.setString(1,userName);
		pstmt.setString(2,userPassword);
		int res = pstmt.executeUpdate();
		
		User[] userArray = new User[100];
		int index = 0;
					
		if(res == 1){
			PreparedStatement retrieveStmt = con.prepareStatement("SELECT * FROM user WHERE "+ "userName=?" + " AND userPassword=?" );
								
			retrieveStmt.setString(1,userName);
			retrieveStmt.setString(2,userPassword);
			ResultSet rs = retrieveStmt.executeQuery();
				
			while(rs.next()){
				int theId4 = rs.getInt("id");
				String userName1 = rs.getString("userName");
				String userPassword1 = rs.getString("userPassword");

				User userObject = new User(theId4,userName1,userPassword1);
				
				
				userArray[index] = userObject;
				index++;
			}
			if(index > 0){
				userArray = Arrays.copyOf(userArray,index);
			}
			return userArray;
		}
		else{
			return null;
		}
	}

	public User[] getUserByIdAndName(String theId, String theName)throws SQLException, ClassNotFoundException, NamingException{
		Connection con = dao.getConnection();
		
		int id = Integer.parseInt(theId);
		
		System.out.println(id+","+theName);
		
		PreparedStatement pstmt = con.prepareStatement("SELECT id, userName, userPassword FROM user WHERE id =? AND userName=?");
		pstmt.setInt(1,id);
		pstmt.setString(2, theName);
		ResultSet rs = pstmt.executeQuery();
		
		User[] userArray = new User[100];
		int index = 0;
	
		while(rs.next()){
			int theId3 = rs.getInt("id");
			String userName = rs.getString("userName");
			String userPassword = rs.getString("userPassword");
			
			System.out.println("In UserFacade.java");
			System.out.println("---"+theId3+","+userName+","+userPassword+"---");
			
			User userObject = new User(theId3,userName,userPassword);
			
			userArray[index] = userObject;
			index++;
		}
		if(index > 0){
			userArray = Arrays.copyOf(userArray,index);
			return userArray;
		}
		else{
			return null;
		}
	}//End Method
	
}