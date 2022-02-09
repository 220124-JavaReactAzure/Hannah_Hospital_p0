package com.revature.p0.menus.startPages;

import java.io.BufferedReader;
import java.io.IOException;

import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.menus.Menu;
import com.revature.p0.services.FacultyService;
import com.revature.p0.services.StudentService;
import com.revature.p0.util.MenuRouter;

public class StudentLoginMenu extends Menu {

	private final StudentService studentService;

	public StudentLoginMenu(BufferedReader bufferedReader, MenuRouter menuRouter, StudentService studentService) {
		super("StudentLogin", "/StudentLoginMenu", bufferedReader, menuRouter);
		this.studentService = studentService;
	
	}

	@Override
	public void renderMenu(){
		try {
		 System.out.println("Please enter your credentials for you account.");
	     System.out.print("Username: ");
	     String username = bufferedReader.readLine();
	     System.out.print("Password: ");
	     String password = bufferedReader.readLine();
	     try {
	    	 boolean result = studentService.validateUser(username, password);
	    	 if(result) {
	    		 studentService.authenticateStudent(username, password);
	    		 menuRouter.transfer("/StudentDashboardMenu");
	    		 

	    	 }
	    	 else {
	    		 System.out.println("You are not an authenticated student. Routing you back to the Welcome Menu...");
	    		 menuRouter.transfer("/welcome");
	    	 }
	    	 
	        } catch (AuthenticationException e) {
	            System.out.println("Incorrect credentials provided! No matching user account found.");
	        }
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	        
	     
	        
	}

}