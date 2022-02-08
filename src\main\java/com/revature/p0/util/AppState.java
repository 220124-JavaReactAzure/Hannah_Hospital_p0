package com.revature.p0.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.revature.p0.daos.CourseDAO;
import com.revature.p0.daos.StudentCourseDAO;
import com.revature.p0.daos.UserDAO;
import com.revature.p0.logging.Logger;
import com.revature.p0.menus.dashboardMenus.FacultyDashboardMenu;
import com.revature.p0.menus.dashboardMenus.StudentDashboardMenu;
import com.revature.p0.menus.startPages.FacultyLoginMenu;
import com.revature.p0.menus.startPages.RegisterStudentMenu;
import com.revature.p0.menus.startPages.StudentLoginMenu;
import com.revature.p0.menus.startPages.WelcomeMenu;
import com.revature.p0.services.FacultyService;
import com.revature.p0.services.StudentService;


public class AppState {

	private final Logger logger;
	private static boolean isRunning;
	private final MenuRouter menuRouter;
	
	public AppState() {
		
		logger = Logger.getLogger(true);
		logger.log("Application  initiliazing...");
		
		isRunning = true;
		menuRouter = new MenuRouter();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		UserDAO userDAO = new UserDAO();
		CourseDAO courseDAO = new CourseDAO();
		StudentCourseDAO studentCourseDAO = new StudentCourseDAO();
		StudentService studentService = new StudentService(userDAO, courseDAO, studentCourseDAO);
		FacultyService facultyService = new FacultyService(userDAO, courseDAO, studentCourseDAO);
		
		menuRouter.addMenu(new WelcomeMenu(bufferedReader, menuRouter));
		menuRouter.addMenu(new StudentLoginMenu(bufferedReader, menuRouter, studentService));
		menuRouter.addMenu(new FacultyLoginMenu(bufferedReader, menuRouter, facultyService));
		menuRouter.addMenu(new RegisterStudentMenu(bufferedReader, menuRouter, studentService));
		menuRouter.addMenu(new FacultyDashboardMenu(bufferedReader, menuRouter, facultyService));
		menuRouter.addMenu(new StudentDashboardMenu(bufferedReader, menuRouter, studentService));
		logger.log("The application has been initialized.");
	}
	
	public void startup() {
		try {
			while(isRunning) {
				menuRouter.transfer("/welcome");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void shutdown() {
		isRunning = false;
	}
	
}