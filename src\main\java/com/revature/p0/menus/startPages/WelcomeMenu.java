package com.revature.p0.menus.startPages;

import java.io.BufferedReader;
import java.io.IOException;
import com.revature.p0.menus.Menu;
import com.revature.p0.models.*;
import com.revature.p0.util.MenuRouter;
import static com.revature.p0.util.AppState.shutdown;


public class WelcomeMenu extends Menu{

	public WelcomeMenu(BufferedReader bufferedReader, MenuRouter menuRouter) {
		super("Welcome", "/welcome", bufferedReader, menuRouter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderMenu(){
		System.out.println("Welcome to the Course Registration Page!\n\" + \"1) Login as a registered student\\n\" + \"2) Login as a registered faculty member\\n\" + \"3) Register as a new student\\n\"+ \"4) Exits Application\\n\" + \">");
		
		try {
		
		String userSelection = bufferedReader.readLine();
		
		switch (userSelection) {
		case "1":
			menuRouter.transfer("/StudentLoginMenu");
			
			break;
		case "2":
			menuRouter.transfer("/FacultyLoginMenu");
			break;
		case "3":
			menuRouter.transfer("/RegisterStudentMenu");
			break;
		
		case "4":
			shutdown();
			break;
		
		default:
			System.out.println("This is not a valid response.");
			break;
		}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}

