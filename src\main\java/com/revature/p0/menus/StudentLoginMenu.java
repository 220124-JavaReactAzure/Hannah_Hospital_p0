package com.revature.p0.menus;

import java.io.BufferedReader;

import com.revature.p0.services.StudentService;
import com.revature.p0.util.MenuRouter;

public class StudentLoginMenu extends Menu{

	
	private final StudentService studentService;

	public StudentLoginMenu(BufferedReader bufferedReader, MenuRouter menuRouter, StudentService studentSerivce) {
		super("Student Login", "/studentLogin", bufferedReader, menuRouter);
		this.studentService = studentService;
	
	}

	@Override
	public void renderMenu() {
		 System.out.println("Please enter your credentials for you account.");
	     System.out.print("Username: ");
	     String username = bufferedReader.readLine();
	     System.out.print("Password: ");
	     String password = bufferedReader.readLine();
	        
	     // Test for a select all
//	     List<Scientist> test = scientistService.getAllScientists();
//	     System.out.println(test.get(0));
	     // Implement an authentication and successful login:
	     try {
	    	 studentService.authenticateStudent(username, password);
	    	 menuRouter.transfer("/dashboard");
	        } catch (AuthenticationException e) {
	            System.out.println("Incorrect credentials provided! No matching user account found.");
	        }
	     
	        
	}

}


}
