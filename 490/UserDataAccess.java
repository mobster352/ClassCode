package com.cs490;

import java.sql.SQLException;
import javax.naming.NamingException;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class UserDataAccess{
	private static UserDataAccess singleton;
	
	private DataSource dataSource;
	
	private UserDataAccess() throws NamingException, SQLException{
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		this.dataSource = (DataSource) envContext.lookup("jdbc/formdb");
		//Connection con = dataSource.getConnection();	
	}
	
	public static UserDataAccess getInstance() throws NamingException, SQLException{
		if(singleton == null)
			singleton = new UserDataAccess();
			
		return singleton;
	}	
	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}