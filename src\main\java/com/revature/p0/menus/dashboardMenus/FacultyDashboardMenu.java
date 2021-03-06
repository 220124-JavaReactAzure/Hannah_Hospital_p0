//package com.revature.p0.menus.dashboardMenus;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//
//import com.revature.p0.menus.Menu;
//import com.revature.p0.models.Course;
//import com.revature.p0.models.User;
//import com.revature.p0.services.FacultyService;
//import com.revature.p0.util.MenuRouter;
//
//public class FacultyDashboardMenu extends Menu {
//
//		private final FacultyService facultyService;
//
//		public FacultyDashboardMenu(BufferedReader bufferedReader, MenuRouter menuRouter, FacultyService facultyService) {
//			super("FacultyDashboard", "/FacultyDashboardMenu", bufferedReader, menuRouter);
//			this.facultyService = facultyService;
//		}
//
//		@Override
//		public void renderMenu(){
//
//			User sessionUser = facultyService.getSessionUser();
//
//			if (sessionUser == null) {
//				System.out.println("You are not logged in! Currently rerouting you to the login screen...");
//				menuRouter.transfer("/FacultyLoginMenu");
//				return;
//			}
//
//			while (facultyService.isSessionActive()) {
//				System.out.println("Welcome " + facultyService.getSessionUser().getFirstName());
//				String menu = "1) Add new classes to the Registration Catalog\n" + 
//						"2) Change/Update the registration details for a course\n" +
//						"3) Remove a class from the registration catalog\n" + 
//						"4) Logout\n" + 
//						 "> ";
//
//				System.out.print(menu);
//				
//			try {
//
//				String userSelection = bufferedReader.readLine();
//
//				switch (userSelection) {
//				case "1":
//					System.out.println("Add new classes to the Registration Catalog");
//					// how can I be sure that the course_id or info the faculty member is providing, has not already been taken
//					System.out.println("Please answer the following questions about the course you wish to add to the Registration Catalog.");
//					System.out.println("What is the Course ID?");
//					int courseID = (int) bufferedReader.read();
//					System.out.println("What is the Course Name?");
//					String courseName = bufferedReader.readLine();
//					System.out.println("What is the Course Department?");
//					String courseDepartment = bufferedReader.readLine();
//					// set the default of availableSlots to 50, and since the faculty member is creating a new course, the total_students =0
//					Course course = new Course(courseID, courseName, courseDepartment, 50, 0);
//					boolean registrationResult = facultyService.registerNewCourse(course);
//					if(registrationResult) {
//						System.out.println("You successfully registered the course: "+courseID);
//					}
//					else {
//						System.out.println("There was a problem with registering a new course.");
//						// should I then transfer to a different menu??
//					}
////					menuRouter.transfer("/FacultyRegistrationMenu");
//					break;
//					
//					
//				case "2":
//					// I am only accounting for one change to the course details to be made as of now.
//					System.out.println("Change/Update the registration details for a course");
//					System.out.println("Enter the course ID of the course you'd like to update: ");
//					int courseIdUpdate = (int)bufferedReader.read();
//					// render the course object with this id
//					Course courseToUpdate = facultyService.findCourseByCourseID(courseIdUpdate);
//					System.out.println("Which aspect of the course would you like to change? \n \1.Course Name\n \2.Course Department\n \3.Available Slots\n \4.Total Students in Course ");
//					String userChoice = bufferedReader.readLine();
//					switch(userChoice) {
//					case "1":
//						System.out.println("What would you like the new Course Name to be? ");
//						String newCourseName = bufferedReader.readLine();
//						courseToUpdate.setCourseName(newCourseName);
//						break;
//					case "2":
//						System.out.println("What would you like the new Course Department to be? ");
//						String newCourseDepartment = bufferedReader.readLine();
//						courseToUpdate.setCourseDepartment(newCourseDepartment);
//						break;
//					case "3":
//						System.out.println("What would you like to set the number of available slots to? ");
//						int newAvailableSlots = (int)bufferedReader.read();
//						courseToUpdate.setAvailableSlots(newAvailableSlots);
//						break;
//					case "4":
//						System.out.println("What would you like to set the number of total students in course to? ");
//						int newTotalStudents = (int)bufferedReader.read();
//						courseToUpdate.setTotalStudentsInCourse(newTotalStudents);
//						break;
//					default:
//						System.out.println("You did not enter valid information for updating the course.");
//						break;
//						// throw some kind of exception, such that it does not reach the code below
//					
//					
//					}
//					// once we have the new course
//					Course updatedCourse = courseToUpdate;
//					boolean updateResult = facultyService.updateCourse(courseToUpdate);
//					if(updateResult) {
//						System.out.println("The course was successfully updated!");
//					}
//					else {
//						System.out.println("The course was not successfully updated.");
//					}
//
////					menuRouter.transfer("/UpdateRegistrationMenu");
//					break;
//					
//					
//				case "3":
//					System.out.println("Remove a class from the registration catalog:");
//					System.out.println("Enter the Course Id of the course you'd wish to remove: ");
//					int courseRemoveId = (int)bufferedReader.read();
//					Course courseToRemove = facultyService.findCourseByCourseID(courseRemoveId);
//					boolean removeResult = facultyService.removeCourse(courseToRemove);
//					if(removeResult) {
//						System.out.println("The course was successfully removed from the Registration Catalog.");
//					}
//					
//					else {
//						System.out.println("The course was not successfully removed from the Registration Catalog.");
//					}
//					
////					menuRouter.transfer("/RemoveFromRegistrationCatalogMenu");
//					break;
//					
//					
//				case "4":
//					facultyService.logout();
//					break;
//				default:
//					System.out.println("The user made an invalid selection");
//				}
//			}
//			catch(IOException e) {
//				e.printStackTrace();
//			}
//				
//			}
//		}
//
//	}