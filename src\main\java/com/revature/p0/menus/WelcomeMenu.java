package com.revature.p0.menus;
import java.io.BufferedReader;
import java.io.IOException;
import com.revature.p0.models.*;

import com.revature.p0.util.MenuRouter;


public class WelcomeMenu extends Menu{

	public WelcomeMenu(BufferedReader bufferedReader, MenuRouter menuRouter) {
		super("Welcome", "/welcome", bufferedReader, menuRouter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderMenu(){
		
		System.out.print(
				"Welcome to the Course Registration Page!\n" + "Please type in 1 for student, and 2 for faculty member. ");
		try {
		
		String userType = bufferedReader.readLine();
		
		switch (userType) {
		case "1":
			menuRouter.transfer("/studentLogin");
			
			break;
		case "2":
			menuRouter.transfer("/facultyLogin");
			break;
		default:
			System.out.println("Please type into the console: 1 or 2.");
			break;
		}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}

