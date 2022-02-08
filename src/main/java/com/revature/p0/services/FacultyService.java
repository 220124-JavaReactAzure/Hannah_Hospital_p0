package com.revature.p0.services;

import com.revature.p0.daos.CourseDAO;
import com.revature.p0.daos.StudentCourseDAO;
import com.revature.p0.daos.UserDAO;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.exceptions.InvalidRequestException;
import com.revature.p0.exceptions.ResourcePersistenceException;
import com.revature.p0.models.Course;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.List;

public class FacultyService {

	private final UserDAO userDAO;
	private final CourseDAO courseDAO;
	private final StudentCourseDAO studentCourseDAO;
	private User sessionUser;

	public FacultyService(UserDAO userDAO, CourseDAO courseDAO, StudentCourseDAO studentCourseDAO) {
		this.userDAO = userDAO;
		this.courseDAO = courseDAO;
		this.studentCourseDAO = studentCourseDAO;
		this.sessionUser = null;
	}
	

	public User getSessionUser() {
		if (sessionUser.getType() == "faculty") {
			return sessionUser;
		}
		throw new AuthenticationException("You are not a faculty member.");

	}
	
	public Course findCourseByCourseID(int courseID) {
		Course course = courseDAO.findByIdAndType(courseID, "course");
		return course;
	}
	

	public List<User> getAllFaculty() {
		return userDAO.findAll("faculty");
	}
	
	
	public boolean validateUser(String userName, String passWord) {
		String type = getSessionUser().getType();
		if(type == "faculty") {
			boolean result = userDAO.validateUser(userName, passWord, type);
			return result;
		}
		else {
			throw new AuthenticationException("You are not a faculty member, you do not have the permissions.");
		}
		
	}
	
	
	
	

	// what am I doing????
	public void authenticateFaculty(String username, String password) {

		if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
			throw new InvalidRequestException(
					"Either username or password is an invalid entry. Please try logging in again");
		}

		User authenticatedUser = userDAO.findByUsernameAndPasswordAndType(username, password, "faculty");

		if (authenticatedUser == null) {
			throw new AuthenticationException(
					"Unauthenticated user, information provided was not found in our database.");
		}
		sessionUser = authenticatedUser;
//		return true;
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

	public boolean registerNewCourse(Course newCourse) {
		// how do I prove that the faculty member has the right credentials, is this
		// enough? I might possibly need more authentication
		if (!isUserValid(sessionUser)) {
			throw new InvalidRequestException("Invalid user data provider");
		}
//		authenticateFaculty(sessionUser.getUserName(), sessionUser.getPassword());

		boolean persistenceResult = courseDAO.create(newCourse);
		if (persistenceResult) {
			Course persistedCourse = newCourse;

			return true;
		}

		else {
			return false;
//			throw new ResourcePersistenceException("The course could not be persisted.");
		}

	}
	
	public boolean updateCourse(Course courseToUpdate) {
		boolean updateResult = courseDAO.update(courseToUpdate);
		if(updateResult) {
			return true;
		}
		else {
			return false;
		}
	}

	// this functionality is for faculty to remove a course from the registrar, I
	// should also unregister all registered students for this course in {}
	public boolean removeCourse(Course courseToRemove) {
		// first ensure that the sessionUser is a faculty member, you must authenticate
		boolean authenticationResult = userDAO.validateUser(sessionUser.getUserName(), sessionUser.getPassword(), sessionUser.getType());
		if (authenticationResult) {
			int courseID = courseToRemove.getCourseId();
			boolean courseDeletionResult = courseDAO.delete(courseID);
			if (!courseDeletionResult) {
				throw new ResourcePersistenceException(
						"This course was not able to be removed from the Course Registration Catalog. ");
			}
			// now, remove all student/course registration instances from the studentcourserecords table
			int courseIdValue = courseToRemove.getCourseId();
			boolean studentCourseDeletionResult = studentCourseDAO.delete(courseIdValue);
			if (studentCourseDeletionResult) {
				return true;
			} else {
				return false;
			}

		} else {
			throw new AuthenticationException("You are not an authenticated faculty member.");
		}
	}
	

}