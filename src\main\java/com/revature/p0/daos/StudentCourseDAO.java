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

	@Override
	public boolean create(StudentCourseInstance obj) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "insert into studentcourserecords(student_course_id, course_id, student_id) values (?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, obj.getStudentCourseId());
			ps.setInt(2, obj.getCourseId());
			ps.setInt(3, obj.getStudentId());
			
			ResultSet rs = ps.executeQuery();
			
			int action = ps.executeUpdate();
			if(action > 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public StudentCourseInstance findByIdAndType(int id, String type) {
		// TODO Auto-generated method stub
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from studentcourserecords where student_course_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				StudentCourseInstance sci = new StudentCourseInstance(id);
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
			Statement s = (Statement) conn.createStatement();

			ResultSet resultSet = ((java.sql.Statement) s).executeQuery(sql);

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
	
	
	@Override
	public boolean update(StudentCourseInstance updatedObject) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "update studentcourserecords set student_course_id = ? ,course_id = ? , student_id = ?, where student_course_id = ? ;";

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

	@Override
	public boolean delete(int id, String type) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "delete from studentcourserecords where student_course_id = ?;";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

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
	
	// the goal of this method is to delete all records in studentcourserecords table that have a certain course_id
	public boolean delete(int courseID) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "delete from studentcourserecords where course_id = ?;";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseID);

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
	

}
	
