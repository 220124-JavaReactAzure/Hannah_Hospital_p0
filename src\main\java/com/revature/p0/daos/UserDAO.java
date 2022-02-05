package com.revature.p0.daos;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.p0.util.collections.ArrayList;
import com.revature.p0.util.collections.List;
import com.revature.p0.util.datasource.ConnectionFactory;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.models.*;

public class UserDAO implements CrudDAO<User> {

	// find the user from the database by username, password, and type(student/faculty)
	public User findByUsernameAndPasswordAndType(String username, String password, String type)
			throws AuthenticationException {
		String sql = "";
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			switch (type) {
			case "student":
				sql = "select * from students where username = ? and password = ?";
				break;
			case "faculty":
				sql = "select * from faculty where username = ? and password = ?";
				break;
			default:
				throw new AuthenticationException("You are neither a student nor a faculty member.");

			}

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setID(rs.getInt(type + "_id"));
				user.setFirstName(rs.getString(type + "_first_name"));
				user.setLastName(rs.getString(type + "_last_name"));
				user.setUserName(rs.getString(type + "_username"));
				user.setPassword(rs.getString(type + "_password"));

				return user;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// find the user by username and Type
	public User findByUsernameAndType(String username, String type) throws AuthenticationException {
		String sql = "";
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			switch (type) {
			case "student":
				sql = "select * from students where username = ?";
				break;
			case "faculty":
				sql = "select * from faculty where username = ?";
				break;
			default:
				throw new AuthenticationException("You are neither a student nor a faculty member.");

			}

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setID(rs.getInt(type + "_id"));
				user.setFirstName(rs.getString(type + "_first_name"));
				user.setLastName(rs.getString(type + "_last_name"));
				user.setUserName(rs.getString(type + "_username"));
				user.setPassword(rs.getString(type + "_password"));

				return user;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// find all users who are students
	@Override
	public List<User> findAllStudents() {

		List<User> studentList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from students";
			Statement s = (Statement) conn.createStatement();

			ResultSet resultSet = ((java.sql.Statement) s).executeQuery(sql);

			while (resultSet.next()) {
				User user = new User();
				user.setID(resultSet.getInt("student_id"));
				user.setFirstName(resultSet.getString("student_first_name"));
				user.setLastName(resultSet.getString("student_last_name"));
				user.setUserName(resultSet.getString("student_username"));
				user.setPassword(resultSet.getString("student_password"));

				studentList.add(user);
			}

			return studentList;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	// find all users who are faculty
	@Override
	public List<User> findAllFaculty() {

		List<User> facultyList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from faculty";
			Statement s = (Statement) conn.createStatement();

			ResultSet resultSet = ((java.sql.Statement) s).executeQuery(sql);

			while (resultSet.next()) {
				User user = new User();
				user.setID(resultSet.getInt("faculty_id"));
				user.setFirstName(resultSet.getString("faculty_first_name"));
				user.setLastName(resultSet.getString("faculty_last_name"));
				user.setUserName(resultSet.getString("faculty_username"));
				user.setPassword(resultSet.getString("faculty_password"));

				facultyList.add(user);
			}

			return facultyList;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public User findByIdAndType(int ID, String type) {
		String sql = "";
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			switch (type) {
			case "student":
				sql = "select * from students where student_id = ?";
				break;
			case "faculty":
				sql = "select * from faculty where faculty_id = ?";
				break;
			default:
				throw new AuthenticationException("You are neither a student nor a faculty member.");

			}

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setID(rs.getInt(type + "_id"));
				user.setFirstName(rs.getString(type + "_first_name"));
				user.setLastName(rs.getString(type + "_last_name"));
				user.setUserName(rs.getString(type + "_username"));
				user.setPassword(rs.getString(type + "_password"));

				return user;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(User updatedUser, String attribute) {
		// TODO Auto-generated method stub
		String userType = updatedUser.getType();
		int userID = updatedUser.getID();
		String sql = "";
		try (Connection conn = ConnectionFactory.getInstance().getConnection()){
			
		switch(userType) {
		case "student":
			sql = "update students set student_id = ? ,student_first_name = ? , student_last_name = ? "
					+ "student_username = ? , student_password = ? , where student_id = ? ;";
			break;

		case "faculty":
			sql = "update faculty set faculty_id = ? ,faculty_first_name = ? , faculty_last_name = ? "
					+ "faculty_username = ? , faculty_password = ? , where faculty_id = ? ;";
			break;
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, userID);
		pstmt.setString(2, updatedUser.getFirstName());
		pstmt.setString(3, updatedUser.getLastName());
		pstmt.setString(4, updatedUser.getUserName());
		pstmt.setString(5, updatedUser.getPassword());
		pstmt.setInt(6, userID);

		
		ResultSet rs = pstmt.executeQuery();
		int count = pstmt.executeUpdate();
		if(count > 0) {
			return true;
		}
		else {
			return false;
		}


		}

	catch (SQLException e) {
		e.printStackTrace();
	}
	return false;

		}
	

	@Override
	public boolean delete(int ID) {
		// TODO Auto-generated method stub
		
		
		return false;
	}

}
