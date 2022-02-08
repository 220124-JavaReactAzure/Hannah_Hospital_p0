package com.revature.p0.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.revature.p0.daos.CourseDAO;
import com.revature.p0.daos.StudentCourseDAO;
import com.revature.p0.daos.UserDAO;
import com.revature.p0.logging.Logger;
import com.revature.p0.services.FacultyService;
import com.revature.p0.services.StudentService;


public class AppState {

	private final Logger logger;
	private static boolean isRunning;
	private final MenuRouter router;
	
	public AppState() {
		
		logger = Logger.getLogger(true);
		logger.log("Application  initiliazing...");
		
		isRunning = true;
		router = new MenuRouter();
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		
		UserDAO userDAO = new UserDAO();
		CourseDAO courseDAO = new CourseDAO();
		StudentCourseDAO studentCourseDAO = new StudentCourseDAO();
		StudentService studentService = new StudentService(userDAO, courseDAO, studentCourseDAO);
		FacultyService facultyService = new FacultyService(userDAO, courseDAO, studentCourseDAO);
		
		router.addMenu(new WelcomeMenu(consoleReader, router));
		router.addMenu(new RegisterMenu(consoleReader, router, scientistService));
		router.addMenu(new LoginMenu(consoleReader, router, scientistService));
		router.addMenu(new DashboardMenu(consoleReader, router, scientistService));
		router.addMenu(new MonsterMenu(consoleReader, router));
		router.addMenu(new MonsterCreationMenu(consoleReader, router, monsterService));
		
		logger.log("The application has been initialized.");
	}
	
	public void startup() {
		try {
			while(isRunning) {
				router.transfer("/welcome");
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