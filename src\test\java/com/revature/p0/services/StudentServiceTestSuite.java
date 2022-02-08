package com.revature.p0.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.revature.p0.daos.CourseDAO;
import com.revature.p0.daos.StudentCourseDAO;
import com.revature.p0.daos.UserDAO;
import com.revature.p0.exceptions.InvalidRequestException;
import com.revature.p0.exceptions.ResourcePersistenceException;
import com.revature.p0.models.User;



public class StudentServiceTestSuite {
	
	StudentService sut;
	UserDAO mockUserDAO;
	CourseDAO mockCourseDAO;
	StudentCourseDAO mockStudentCourseDAO;
	
	
	
	@Before
	public void testPrep() {
		mockUserDAO = mock(UserDAO.class);
		sut = new StudentService(mockUserDAO, mockCourseDAO, mockStudentCourseDAO);
	}
	
	
	@Test
	public void test_isUserValid_returnsTrue_givenValidUser() {
		
		// Arrange
		User validUser = new User("valid", 0, "valid", "valid", "valid", "valid");
		
		// Act
		boolean actualResult = sut.isUserValid(validUser);
		
		// Assert
		Assert.assertTrue(actualResult);
		
	}
	
	@Test
	public void test_isUserValid_returnsFalse_givenUserWithType() {
		
		// Arrange : create invalid Users that have invalid arguments for the first entry, or type for User
		User invalidUser1 = new User("",0,"valid","valid","valid", "valid");
		User invalidUser2 = new User(null,0,"valid","valid","valid", "valid");
		User invalidUser3 = new User("         ",0,"valid","valid","valid","valid");
		
		
		//Act
		boolean actualResult1 = sut.isUserValid(invalidUser1);
		boolean actualResult2 = sut.isUserValid(invalidUser2);
		boolean actualResult3 = sut.isUserValid(invalidUser3);
		
		//Assert - everything you assert must pass the condition
		Assert.assertFalse(actualResult1);
		Assert.assertFalse(actualResult2);
		Assert.assertFalse(actualResult3);
		
	}
	
	@Test
	public void test_registerUser_returnsTrue_givenValidScientist() {
		// Arrange
		User validUser = new User("valid",0,"valid","valid","valid","valid");
		when(mockUserDAO.findByUsernameAndType(validUser.getUserName(), validUser.getType())).thenReturn(null);
		when(mockUserDAO.findByUsernameAndPasswordAndType(validUser.getUserName(), validUser.getPassword(), validUser.getType())).thenReturn(null);
		when(mockUserDAO.create(validUser)).thenReturn(validUser);
		
		// Act
		User actualResult = sut.registerNewStudent(validUser);
		
		// Assert
		Assert.assertNotNull(actualResult);
		verify(mockUserDAO, times(1)).create(validUser);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void test_registerUser_throwsInvalidRequestException_givenInvalidUser() {
		sut.registerNewStudent(null);
	}
	
	
	
	
	@Test(expected = ResourcePersistenceException.class)
	public void test_registerUser_throwsResourcePersistenceException_givenUserWithTakenUsername() {
		
		// Arrange
		User usernameTakenUser = new User("valid",0,"valid","valid","valid","valid");
		when(mockUserDAO.findByUsernameAndType(usernameTakenUser.getUserName(), usernameTakenUser.getType())).thenReturn(usernameTakenUser);
		when(mockUserDAO.findByUsernameAndPasswordAndType(usernameTakenUser.getUserName(),usernameTakenUser.getPassword(), usernameTakenUser.getType() )).thenReturn(null);
		when(mockUserDAO.create(usernameTakenUser)).thenReturn(usernameTakenUser);
		
		// Act
		try {
			sut.registerNewStudent(usernameTakenUser);
		} finally {
			// Assert
			verify(mockUserDAO, times(0)).create(usernameTakenUser);
		}
	}
	
	
	
	

}
