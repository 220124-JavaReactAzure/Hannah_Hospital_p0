package com.revature.p0.menus.dashboardMenus;

import java.io.BufferedReader;
import java.io.IOException;

import com.revature.p0.menus.Menu;
import com.revature.p0.models.User;
import com.revature.p0.services.FacultyService;
import com.revature.p0.util.MenuRouter;

public class FacultyDashboardMenu extends Menu {

		private final FacultyService facultyService;

		public FacultyDashboardMenu(BufferedReader bufferedReader, MenuRouter menuRouter, FacultyService facultyService) {
			super("FacultyDashboard", "/FacultyDashboardMenu", bufferedReader, menuRouter);
			this.facultyService = facultyService;
		}

		@Override
		public void renderMenu(){

			User sessionUser = facultyService.getSessionUser();

			if (sessionUser == null) {
				System.out.println("You are not logged in! Currently rerouting you to the login screen...");
				menuRouter.transfer("/FacultyLoginMenu");
				return;
			}

			while (facultyService.isSessionActive()) {
				System.out.println("Welcome " + facultyService.getSessionUser().getFirstName());
				String menu = "1) Add new classes to the Registration Catalog\n" + 
						"2) Change/Update the registration details for a course\n" +
						"3) Remove a class from the registration catalog\n" + 
						"4) Logout\n" + 
						 "> ";

				System.out.print(menu);
				
			try {

				String userSelection = bufferedReader.readLine();

				switch (userSelection) {
				case "1":
					System.out.println("Add new classes to the Registration Catalog");
					menuRouter.transfer("/FacultyRegistrationMenu");
					break;
				case "2":
					System.out.println("Change/Update the registration details for a course");
					menuRouter.transfer("/UpdateRegistrationMenu");
					break;
				case "3":
					System.out.println("Remove a class from the registration catalog:");
					menuRouter.transfer("/RemoveFromRegistrationCatalogMenu");
					break;
				case "4":
					facultyService.logout();
					break;
				default:
					System.out.println("The user made an invalid selection");
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
				
			}
		}

	}