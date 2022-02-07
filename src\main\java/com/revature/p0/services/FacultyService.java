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

	public List<User> getAllFaculty() {
		return userDAO.findAll("faculty");
	}

	// TODO: Impelement authentication
	public boolean authenticateFaculty(String username, String password) {

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
		return true;
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

	public Course registerNewCourse(Course newCourse) {
		// how do I prove that the faculty member has the right credentials, is this
		// enough? I might possibly need more authentication
		if (!isUserValid(sessionUser)) {
			throw new InvalidRequestException("Invalid user data provider");
		}
//		authenticateFaculty(sessionUser.getUserName(), sessionUser.getPassword());

		boolean persistenceResult = courseDAO.create(newCourse);
		if (persistenceResult) {
			Course persistedCourse = newCourse;

			return persistedCourse;
		}

		else {
			throw new ResourcePersistenceException("The course could not be persisted.");
		}

	}

	// this functionality is for faculty to remove a course from the registrar, I
	// should also unregister all registered students for this course in {}
	public boolean removeCourse(Course courseToRemove) {
		// first ensure that the sessionUser is a faculty member, you must authenticate
		// them
		boolean authenticationResult = this.authenticateFaculty(sessionUser.getUserName(), sessionUser.getPassword());
		if (authenticationResult) {
			int courseID = courseToRemove.getCourseId();
			boolean courseDeletionResult = courseDAO.delete(courseID, "course");
			if (!courseDeletionResult) {
				throw new ResourcePersistenceException(
						"This course was not able to be removed from the Course Registration Catalog. ");
			}
			// now, remove all student/course registration instances from the
			// studentcourserecords table
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