package com.revature.p0.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.p0.daos.CourseDAO;
import com.revature.p0.daos.StudentCourseDAO;
import com.revature.p0.daos.UserDAO;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.exceptions.InvalidRequestException;
import com.revature.p0.exceptions.ResourcePersistenceException;
import com.revature.p0.models.Course;
import com.revature.p0.models.StudentCourseInstance;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.List;
import com.revature.p0.util.datasource.ConnectionFactory;

public class StudentService {

	private final UserDAO userDAO;
	private final StudentCourseDAO studentCourseDAO;
	private final CourseDAO courseDAO;
	private User sessionUser;
	
	
	public StudentService(UserDAO userDAO,CourseDAO courseDAO, StudentCourseDAO studentCourseDAO) {
		this.userDAO = userDAO;
		this.courseDAO = courseDAO;
		this.studentCourseDAO = studentCourseDAO;
		this.sessionUser = null;

	}

	public User getSessionUser() {
		return sessionUser;
	}

	public User registerNewStudent(User newUser) {
		if (!isUserValid(newUser)) {
			throw new InvalidRequestException("Invalid user data provider");
		}

//		boolean usernameAvailable = userDAO.findByUsernameAndType(newUser.getUserName(), newUser.getType()) == null;
//		// testing
//		System.out.println(usernameAvailable);
//		//
//		if (!usernameAvailable) {
//				throw new ResourcePersistenceException("The provided username was already taken in the database");
//		}

//		boolean persistenceResult = userDAO.create(newUser);
		userDAO.create(newUser);
		return newUser;
//		if (persistenceResult) {
//			User persistedUser = newUser;
//			
//
//			return persistedUser;
//		}
//
//		else {
//			throw new ResourcePersistenceException("The student could not be persisted.");
//		}

	}


	// works well
	public void viewAvailableCourses() {
		// if the student is valid and a registered student, they can access this
		// functionality
		List<Course> availableCourses = courseDAO.findAllAvailableCourses();
		if (availableCourses.size() > 0) {
			System.out.println("Here are the current available courses: ");
			for (int i = 0; i < availableCourses.size(); i++) {
				String result = "Course ID: " + availableCourses.get(i).getCourseId() + " | Course Name: "
						+ availableCourses.get(i).getCourseName() + " | Course Department: "
						+ availableCourses.get(i).getCourseDepartment() + "";
				System.out.println(result);
				System.out.println();

			}
		} 
		else {
			System.out.println("There are currently no available courses.");
			System.out.println();
		}

	}
	
	// returns the SCI from the SCDAO, which will connect to db
	public StudentCourseInstance getStudentCourseInstance(int courseID) {
		StudentCourseInstance sci = studentCourseDAO.findByIdAndType(courseID, "course");
		return sci;
//		if(sci == null) {
//			throw new InvalidRequestException("The student is not registered for this course.");
//		}
//		else {
//			return sci;
//		}
	}
	
	
//	// this creates a studentCourseInstance, so that the student can then register for a course
//	public StudentCourseInstance createStudentCourseInstance(int courseID) {
//		StudentCourseInstance = new StudentCourseInstance()
//	}
	
	
	
	
	// look here 
	public void registerForCourse(StudentCourseInstance sci) {
		boolean isAvailable = courseDAO.isCourseAvailable(sci.getCourseId());
		if(isAvailable) {
			studentCourseDAO.createRecord(sci);
		}
		else {
			throw new ResourcePersistenceException("This course is not available. There are no more available slots.");
		}
		
	}
	
	
	
	// to cancel your registration for a certain course
	// should I include some functionality to make sure that the sci is authenticated and valid??
	public void cancelRegistration(StudentCourseInstance sci) {
		int courseID = sci.getCourseId();
		studentCourseDAO.deleteRecord(courseID);
//		if(deletionResult) {
//			return true;
//		}
//		else {
//			return false;
//		}
	}
	
	
	
	// to view all of the courses that the current session user is registered for
	public void viewMyRegisteredCourses() {
		int studentID = sessionUser.getID();
		List<StudentCourseInstance> myListSCI = studentCourseDAO.findAllRegisteredCourses(studentID);
		if(myListSCI.size() > 0) {
			System.out.println("These are the courses that you are registered for: ");
			List<Course> registeredCourses = courseDAO.findAllRegisteredCoursesForStudent(studentID);
			for(int i = 0; i< registeredCourses.size(); i++) {
				System.out.println(registeredCourses.get(i).getCourseName());
			}
		}
		else {
			System.out.println("You are not currently registered for any courses.");
		}
		
	}
	
	
	public void viewMyRegisteredCourses(int studentID) {
		List<StudentCourseInstance> myListSCI = studentCourseDAO.findAllRegisteredCourses(studentID);
//		if(myListSCI.size()>0) {
//			System.out.println("These are the courses you are registered for: ");
//			for(int i = 0; i< myListSCI.size(); i++) {
//				System.out.println(myListSCI.get(i).getCourseId());
//			}
//		}
		// it's not reaching the courseDAO code
		if(myListSCI.size() > 0) {
			System.out.println();
			System.out.println("These are the courses that you are registered for: ");
			List<Course> registeredCourses = courseDAO.findAllRegisteredCoursesForStudent(studentID);
			for(int i = 0; i< registeredCourses.size(); i++) {
				System.out.println(registeredCourses.get(i).getCourseName());
			}
		}
		else {
			System.out.println("You are not currently registered for any courses.");
		}
		
	}
	

	public List<User> getAllStudents() {
		return userDAO.findAll("student");
	}

	

	public void authenticateStudent(String username, String password) {

		if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
			throw new InvalidRequestException(
					"Either username or password is an invalid entry. Please try logging in again");
		}

		User authenticatedUser = userDAO.findByUsernameAndPasswordAndType(username, password, "student");

		if (authenticatedUser == null) {
			throw new AuthenticationException(
					"Unauthenticated user, information provided was not found in our database.");
		}
		sessionUser = authenticatedUser;
	}

	
	
	
	public boolean isUserValid(User newUser) {
		if (newUser == null)
			return false;
		if (newUser.getFirstName() == null || newUser.getFirstName().trim().equals(""))
			return false;
		if (newUser.getLastName() == null || newUser.getLastName().trim().equals(""))
			return false;
		if (newUser.getUserName() == null || newUser.getUserName().trim().equals(""))
			return false;
		return newUser.getPassword() != null || !newUser.getPassword().trim().equals("");

	}
	
	public boolean validateUser(String userName, String passWord) {
		boolean result = userDAO.validateUser(userName, passWord, "student");
		return result;
	}
	

	public void logout() {
		sessionUser = null;
	}

	public boolean isSessionActive() {
		return sessionUser != null;
	}

}