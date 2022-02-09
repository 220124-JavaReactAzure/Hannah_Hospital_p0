package com.revature.p0.menus.startPages;

import java.io.BufferedReader;
import java.io.IOException;

import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.menus.Menu;
import com.revature.p0.models.User;
import com.revature.p0.services.StudentService;
import com.revature.p0.util.MenuRouter;

public class RegisterStudentMenu extends Menu {

	private final StudentService studentService;
	private static int studentIdTracker = 2;

	public RegisterStudentMenu(BufferedReader bufferedReader, MenuRouter menuRouter, StudentService studentService) {
		super("RegisterStudent", "/RegisterStudentMenu", bufferedReader, menuRouter);
		this.studentService = studentService;
	
	}

	public void renderMenu(){
		try {
		 System.out.println("Welcome to the student registration page.");
		 int studentID = studentIdTracker;
		 System.out.println("Please provide us with some information about yourself.");
	     System.out.print("First name: ");
	     String firstName = bufferedReader.readLine();
	     System.out.print("Last name: ");
	     String lastName = bufferedReader.readLine();
	     System.out.print("Username: ");
	     String userName = bufferedReader.readLine();
	     System.out.print("Password: ");
	     String passWord = bufferedReader.readLine();
	     User user = new User("student", studentID, firstName, lastName, userName, passWord);
	     User newUser = studentService.registerNewStudent(user);
	     if(user.getID() == newUser.getID()) {
	    	 studentIdTracker++;
		     System.out.println("You have successfully been registered as a student!");
		     System.out.println("Routing you back to the login menu...");
		     menuRouter.transfer("/StudentLoginMenu");
	     }
	     else {
	    	 System.out.println("You have not been successfully registrered as a new student. Routing you back to the welcome menu.");
	    	 menuRouter.transfer("/welcome");
	     }

	     
		}
		catch(IOException e) {
			e.printStackTrace();
			}
		}
	     
	     
	     
	  

}