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

		return sessionUser;

	}

	public Course findCourseByCourseID(int courseID) {
		Course course = courseDAO.findByIdAndType(courseID, "course");
		return course;
	}

	public List<User> getAllFaculty() {
		return userDAO.findAll("faculty");
	}
	
	public boolean validateUser(String userName, String passWord) {
		boolean result = userDAO.validateUser(userName, passWord, "faculty");
		return result;
	}
	
	

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

	
	public void registerNewCourse(Course newCourse) {
		if (!isUserValid(sessionUser)) {
			throw new InvalidRequestException("Invalid user data provider");
		}

		courseDAO.createRecord(newCourse);

	}
	

	public boolean updateCourse(Course courseToUpdate) {
		boolean updateResult = courseDAO.update(courseToUpdate);
		if (updateResult) {
			return true;
		} else {
			return false;
		}
	}
	
	public void removeCourse(Course courseToRemove) {

			int courseID = courseToRemove.getCourseId();
			// delete from registrationcatalog
			courseDAO.deleteRecord(courseID);
			//remove all records from studentcourserecords that have course_id=courseID
			studentCourseDAO.deleteRecord(courseID);
		} 
	
	
	
	
	
	
	
	
	
	

}