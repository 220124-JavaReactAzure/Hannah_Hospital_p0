package com.revature.p0.services;

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

public class StudentService {

	private final UserDAO userDAO;
	private final StudentCourseDAO studentCourseDAO;
	private final CourseDAO courseDAO;
	private User sessionUser;

	public StudentService(UserDAO userDAO, StudentCourseDAO studentCourseDAO, CourseDAO courseDAO) {
		this.userDAO = userDAO;
		this.studentCourseDAO = studentCourseDAO;
		this.courseDAO = courseDAO;
		this.sessionUser = null;

	}

	public User getSessionUser() {
		return sessionUser;
	}

	// this method is only to register new students, NOT faculty, I will figure out
	// that implementation later
	public User registerNewStudent(User newUser) {
		if (!isUserValid(newUser)) {
			throw new InvalidRequestException("Invalid user data provider");
		}

		boolean usernameAvailable = userDAO.findByUsernameAndType(newUser.getUserName(), newUser.getType()) == null;

		if (!usernameAvailable) {
				throw new ResourcePersistenceException("The provided username was already taken in the database");
		}

		boolean persistenceResult = userDAO.create(newUser);
		if (persistenceResult) {
			User persistedUser = newUser;

			return persistedUser;
		}

		else {
			throw new ResourcePersistenceException("The student could not be persisted.");
		}

	}

// I NEED ALL THIS FUNCTIONALITY
// view all available courses: done
//	 register for an open and available class : done
//	 cancel my registration for a class (if within window)
//	 view the classes that I have registered for

	public void viewAvailableCourses() {
		// if the student is valid and a registered student, they can access this
		// functionality
		List<Course> availableCourses = courseDAO.findAllAvailableCourses();
		if (availableCourses.size() > 0) {
			System.out.println("Here are the current available courses: ");
			for (int i = 0; i < availableCourses.size(); i++) {
				String result = "Course ID: " + availableCourses.get(i).getCourseId() + " ; Course Name: "
						+ availableCourses.get(i).getCourseName() + " ; Course Department: "
						+ availableCourses.get(i).getCourseDepartment() + "";
				System.out.println(result);

			}
		} 
		else {
			System.out.println("There are currently no available courses.");
		}

	}
	
	public boolean registerForCourse(StudentCourseInstance sci) {
		boolean isAvailable = courseDAO.isCourseAvailable(sci.getCourseId());
		if(isAvailable) {
			boolean studentCourseSuccess = studentCourseDAO.create(sci);
			if(studentCourseSuccess) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			throw new ResourcePersistenceException("This course is not available. There are no more available slots.");
		}
		
	}
	
	// to cancel your registration for a certain course
	// should I include some functionality to make sure that the sci is authenticated and valid??
	public boolean cancelRegistration(StudentCourseInstance sci) {
		int courseID = sci.getCourseId();
		boolean deletionResult = studentCourseDAO.delete(courseID);
		if(deletionResult) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// to view all of the courses that you registered for
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
	

	public List<User> getAllStudents() {
		return userDAO.findAll("student");
	}

	// TODO: Impelement authentication
	public void authenticateStudents(String username, String password) {

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

	public void logout() {
		sessionUser = null;
	}

	public boolean isSessionActive() {
		return sessionUser != null;
	}

}