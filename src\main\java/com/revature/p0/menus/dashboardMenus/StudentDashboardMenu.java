package com.revature.p0.menus.dashboardMenus;

import java.io.BufferedReader;
import java.io.IOException;

import com.revature.p0.exceptions.ResourcePersistenceException;
import com.revature.p0.menus.Menu;
import com.revature.p0.models.StudentCourseInstance;
import com.revature.p0.models.User;
import com.revature.p0.services.FacultyService;
import com.revature.p0.services.StudentService;
import com.revature.p0.util.MenuRouter;

public class StudentDashboardMenu extends Menu {

		private final StudentService studentService;

		public StudentDashboardMenu(BufferedReader bufferedReader, MenuRouter menuRouter, StudentService studentService) {
			super("StudentDashboard", "/StudentDashboardMenu", bufferedReader, menuRouter);
			this.studentService = studentService;
		}

		@Override
		public void renderMenu(){

			User sessionUser = studentService.getSessionUser();

			if (sessionUser == null) {
				System.out.println("You are not logged in! Currently rerouting you to the login screen...");
				menuRouter.transfer("/StudentLoginMenu");
				return;
			}

			while (studentService.isSessionActive()) {
				System.out.println("Welcome " + studentService.getSessionUser().getFirstName());
				String menu = "1) View all classes available for registration\n" + 
						"2) Register for an open and available class\n" +
						"3) Cancel your registration for a class\n" + 
						"4) View all of your registered courses\n" + 
						"5) Logout\n" + 
						 "> ";

				System.out.print(menu);
				
			try {

				String userSelection = bufferedReader.readLine();

				switch (userSelection) {
				case "1":
					System.out.println("View all classes available for registration");
					studentService.viewAvailableCourses();
					
					// do i even need to transfer to a new menu
//					menuRouter.transfer("/StudentCourseRegistrationMenu");
					
					break;
				
				
				// how do I implement a StudentCourseInstance from here?? should I, or where should I do that??
				case "2":
					System.out.println("Register for an open and available class");
					System.out.println("Type into the console, the course ID of the class you wish to register for: ");
					// will this casting work??
					int courseID = (int) bufferedReader.read();
					StudentCourseInstance sci = studentService.getStudentCourseInstance(courseID);
					boolean successfulRegistration = studentService.registerForCourse(sci);
					if(successfulRegistration) {
						System.out.println("You succcessfully registered for course: "+ courseID);
					}
					else {
						System.out.println("You were not able to successfully register for course: "+courseID);
						// Do i need to route them back to a menu??
						}
					
					
//					menuRouter.transfer("/StudentCourseRegistration");
					break;
				case "3":
					System.out.println("Cancel your registration for a class");
					System.out.println("Type the course ID of course you would like to cancel your registration for.");
					int courseIdDelete = (int) bufferedReader.read();
					StudentCourseInstance SCI = studentService.getStudentCourseInstance(courseIdDelete);
					boolean deletionResult = studentService.cancelRegistration(SCI);
					if(deletionResult) {
						System.out.println("You successfully deleted course: "+ courseIdDelete);
					}
					else {
						throw new ResourcePersistenceException("There was an issue with deleting your course: "+courseIdDelete);
						// should I reroute them somewhere??
					}

//					menuRouter.transfer("/RemoveFromRegistrationCatalogMenu");
					break;
				
				case "4":
					System.out.println("View all of your registered courses");
					studentService.viewMyRegisteredCourses(sessionUser.getID());
					
					
//					menuRouter.transfer("/RemoveFromRegistrationCatalogMenu");
					break;
					
				case "5":
					studentService.logout();
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