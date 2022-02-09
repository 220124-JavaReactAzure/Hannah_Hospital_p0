package com.revature.p0.daos;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.models.Course;
import com.revature.p0.models.StudentCourseInstance;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.ArrayList;
import com.revature.p0.util.collections.List;
import com.revature.p0.util.datasource.ConnectionFactory;

public class StudentCourseDAO implements CrudDAO<StudentCourseInstance> {

	
	public boolean createRecord(StudentCourseInstance obj) {
		// TODO Auto-generated method stub
		// include functionality so that a studentCourseInstance can only be made if the available_slots > 0 in the registrationCatalog. I want to get a boolean as a result
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "insert into studentcourserecords(student_course_id, course_id, student_id) values (?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, obj.getStudentCourseId());
			ps.setInt(2, obj.getCourseId());
			ps.setInt(3, obj.getStudentId());
			
			ResultSet rs = ps.executeQuery();
			return true;
			
//			int action = ps.executeUpdate();
//			if(action > 0) {
//				return true;
//			}
//			else {
//				return false;
//			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	@Override
	public void create(StudentCourseInstance obj) {
		System.out.println("hi");
	}
	
	

	@Override
	public StudentCourseInstance findByIdAndType(int id, String type) {
		// TODO Auto-generated method stub
		// where id = the course ID
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from studentcourserecords where course_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				StudentCourseInstance sci = new StudentCourseInstance();
				sci.setStudentCourseId(rs.getInt("student_course_id"));
				sci.setCourseId(rs.getInt("course_id"));
				sci.setStudentId(rs.getInt("student_id"));
				return sci;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public List<StudentCourseInstance> findAll(String type) {
		List<StudentCourseInstance> studentCourseList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from studentcourserecords";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				StudentCourseInstance sci = new StudentCourseInstance();
				sci.setStudentCourseId(resultSet.getInt("student_course_id"));
				sci.setCourseId(resultSet.getInt("course_id"));
				sci.setStudentId(resultSet.getInt("student_id"));


				studentCourseList.add(sci);
			}

			return studentCourseList;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	// find all the courses that a particular student is registered for, it returns a list of the studentCourseInstances
	public List<StudentCourseInstance> findAllRegisteredCourses(int studentID){
		// if the student is valid, and authenticated. include some functionality for that first
		List<StudentCourseInstance> listSCI = new ArrayList<StudentCourseInstance>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "select * from studentcourserecords where student_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				StudentCourseInstance sci = new StudentCourseInstance();
				sci.setStudentCourseId(rs.getInt("student_course_id"));
				sci.setCourseId(rs.getInt("course_id"));
				sci.setStudentId(rs.getInt("student_id"));
				listSCI.add(sci);
				
			}
			return listSCI;
			
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return listSCI;
	}
	
	
	
	
	@Override
	public boolean update(StudentCourseInstance updatedObject) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "update studentcourserecords set student_course_id = ? ,course_id = ? , student_id = ?, where student_course_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, updatedObject.getStudentCourseId());
			pstmt.setInt(2, updatedObject.getCourseId());
			pstmt.setInt(3, updatedObject.getStudentId());
			pstmt.setInt(4, updatedObject.getStudentCourseId());
			ResultSet rs = pstmt.executeQuery();
			// this is to check if there were any updates, if successful return true if not
			// return false
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			} else {
				return false;
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	
	
	// the goal of this method is to delete all records in studentcourserecords table that have a certain course_id
	@Override
	public boolean delete(int ID) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "delete from studentcourserecords where student_course_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ID);

			ResultSet rs = pstmt.executeQuery();
			// this is to check if there were any rows affected, if successful return true
			// if not return false
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			} else {
				return false;
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	// look at this one
	public void deleteRecord(int ID) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "delete from studentcourserecords where course_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ID);

			ResultSet rs = pstmt.executeQuery();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}


	}
	

}
	
