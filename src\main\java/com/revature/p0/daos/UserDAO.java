package com.revature.p0.daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.p0.util.collections.List;
import com.revature.p0.util.datasource.ConnectionFactory;
import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.models.*;


public class UserDAO implements CrudDAO<User> {
	
	//Implement Authentication
	public User findByUsernameAndPasswordAndType(String username, String password, int userType) throws AuthenticationException {
		String sql = "";
		String type = "";
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			switch(userType) {
			case 1:
				sql = "select * from students where username = ? and password = ?";
				type = "student";
				break;
			case 2:
				sql = "select * from faculty where username = ? and password = ?";
				type = "faculty";
				break;
			default:
				throw new AuthenticationException("You are neither a student or faculty member.");
				
			}
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setID(rs.getInt(type + "_ID"));
				user.setFirstName(rs.getString(type + "_first_name"));
				user.setLastName(rs.getString(type + "_last_name"));
				user.setUserName(rs.getString(type + "_user_name"));
				user.setPassword(rs.getString(type + "_password"));
				
				return user;
				
			}
			
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
}
