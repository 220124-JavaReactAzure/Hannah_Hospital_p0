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
		private static int studentCourseInstanceIDTracker = 100;

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
				System.out.println();
				System.out.println("Welcome " + studentService.getSessionUser().getFirstName());
				System.out.println("Read the instructions below, and type into the console what you wish to do.");
				String menu = "1) View all classes available for registration\n" + 
						"2) Register for an open and available class\n" +
						"3) Cancel your registration for a class\n" + 
						"4) View all of your registered courses\n" + 
						"5) Logout\n" + 
						 "> ";

				System.out.print(menu);
				System.out.println();
				
			try {

				String userSelection = bufferedReader.readLine();

				switch (userSelection) {
				case "1":
					System.out.println("View all classes available for registration");
					studentService.viewAvailableCourses();				
					break;
				
				case "2":
					System.out.println("Register for an open and available class");
					System.out.println("Type into the console, the course ID of the class you wish to register for: ");

					String courseId = bufferedReader.readLine();
					int courseID = Integer.parseInt(courseId);
					StudentCourseInstance sci = new StudentCourseInstance(courseID);
					sci.setStudentId(sessionUser.getID());
					sci.setStudentCourseId(studentCourseInstanceIDTracker);
					studentService.registerForCourse(sci);
					System.out.println("You succcessfully registered for course: "+ courseID);
					studentCourseInstanceIDTracker++;

					break;
				case "3":
					System.out.println("Cancel your registration for a class");
					System.out.print("Type the course ID of course you would like to cancel your registration for: ");
					String courseIDDelete = bufferedReader.readLine();
					int courseIdDelete = Integer.parseInt(courseIDDelete);
					StudentCourseInstance SCI = studentService.getStudentCourseInstance(courseIdDelete);
					studentService.cancelRegistration(SCI);
					break;
				
				case "4":
					System.out.println("View all of your registered courses");
					studentService.viewMyRegisteredCourses(sessionUser.getID());
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