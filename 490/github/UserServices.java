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

@Path("/user")
public class UserServices{
	
	
	@Path("/{userid}")
	@PUT
	@Produces("text/plain")
	@Consumes("application/x-www-form-urlencoded")
	public Response updateUser(@PathParam("userid") int id, MultivaluedMap<String,String> formFields) throws SQLException, ClassNotFoundException, NamingException {
		UserFacade userFacade = UserFacade.getInstance();
			
		String userNameUpdate = formFields.getFirst("username");
		String userPasswordUpdate = formFields.getFirst("password");
		
		System.out.println("servicesid:"+id);
		System.out.println("ServicesN:"+userNameUpdate);
		System.out.println("ServicesP:"+userPasswordUpdate);
		
		User userToUpdate = new User(userNameUpdate,userPasswordUpdate);

		User[] resultArray = userFacade.updateUser(id,userToUpdate);
		
		if(resultArray != null){
			Gson theGsonObject = new Gson();
			String result = theGsonObject.toJson(resultArray);
			ResponseBuilder rb = Response.ok(result,MediaType.TEXT_PLAIN);
			rb.status(201);
			return rb.build();
		}
		else{
			return Response.status(404).build();	
		}//end else
	}//End method
	
	@POST
	@Produces("text/plain")
	@Consumes("application/x-www-form-urlencoded") //web service expected from data
	public Response createUser(MultivaluedMap<String,String> formFields) throws SQLException, ClassNotFoundException, NamingException {
		UserFacade userFacade = UserFacade.getInstance();
	
		String userName = formFields.getFirst("newName");
		String userPassword = formFields.getFirst("newPassword");	
		
		//System.out.println("userName"+userName);//empty for some reason
		//System.out.println("userPassword"+userPassword);
		
		User userToAdd = new User(userName,userPassword);
		User[] resultArray = userFacade.createUser(userToAdd);
		
		
		if(resultArray != null){
			Gson theGsonObject = new Gson();
			String result = theGsonObject.toJson(resultArray);
			ResponseBuilder rb = Response.ok(result,MediaType.TEXT_PLAIN);
			rb.status(201);
			return rb.build();
		}
		else{
			return Response.status(404).build();	
		}//end else
	}//End method
	
	@Path("/{userid}")
	@DELETE
	@Produces("text/plain")
	@Consumes("application/x-www-form-urlencoded") 
	public Response deleteUser(@PathParam("userid") int id) throws SQLException, ClassNotFoundException,NamingException
	{
		UserFacade userFacade = UserFacade.getInstance();
		int result1 = userFacade.deleteUser(id);
		
		if(result1 != 0){
				Gson theGsonObject = new Gson();
				String result = theGsonObject.toJson(result1);
				ResponseBuilder rb = Response.ok(result,MediaType.TEXT_PLAIN);
				rb.status(201);
				return rb.build();
			}
			else{
				return Response.status(404).build();	
			}//end else
	}//End method	

	@Path("/theuser/")
	@GET
	@Produces("text/plain")
	public Response getUser(@QueryParam("uId")String theId, @QueryParam("uName") String theName) throws SQLException, ClassNotFoundException, NamingException { 
		//Get reference to the ingredientFacade singleton object
		UserFacade userFacade = UserFacade.getInstance();	
		User[] resultArray = userFacade.getUserByIdAndName(theId,theName);
		
		if(resultArray != null){
			Gson theGsonObject = new Gson();
			String result = theGsonObject.toJson(resultArray);
			ResponseBuilder rb = Response.ok(result,MediaType.TEXT_PLAIN);
			rb.status(200);
			return rb.build();
		}
		else{
			return Response.status(404).build();
		}
	}
}