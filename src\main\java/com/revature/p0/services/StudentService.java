package com.revature.p0.services;

import com.revature.p0.daos.UserDAO;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.exceptions.InvalidRequestException;
import com.revature.p0.exceptions.ResourcePersistenceException;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.List;

public class StudentService {

	private final UserDAO userDAO;
	private User sessionUser;

	public StudentService(UserDAO userDAO) {
		this.userDAO = userDAO;
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
			if (!usernameAvailable) {
				throw new ResourcePersistenceException("The provided username was already taken in the database");
			}
		}

		boolean persistenceResult = userDAO.create(newUser);
		if (persistenceResult) {
			User persistedUser = newUser;


			return persistedUser;}
		
		else {
			throw new ResourcePersistenceException("The student could not be persisted.");
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