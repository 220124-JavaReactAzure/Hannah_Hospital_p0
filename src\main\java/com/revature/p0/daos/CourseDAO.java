package com.revature.p0.daos;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.p0.exceptions.AuthenticationException;
import com.revature.p0.models.Course;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.ArrayList;
import com.revature.p0.util.collections.List;
import com.revature.p0.util.datasource.ConnectionFactory;

public class CourseDAO implements CrudDAO<Course> {

	// this is to find a certain record in a table, given the courseID and return a
	// course object containing all the info from the table
	public Course findByIdAndType(int courseId, String type) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from registrationCatalog where course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Course course = new Course(courseId);
				course.setCourseName(rs.getString("course_name"));
				course.setCourseDepartment(rs.getString("course_department"));
				course.setAvailableSlots(rs.getInt("available_slot"));
				course.setTotalStudentsInCourse(rs.getInt("total_students_in_course"));

				return course;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	// this is for faculty members to create a new course object and register it
	// into the database
	public boolean create(Course obj) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "insert into registrationcatalog(course_id, course_name, course_department, available_slots, total_students_in_course) values (?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, obj.getCourseId());
			pstmt.setString(2, obj.getCourseName());
			pstmt.setString(3, obj.getCourseDepartment());
			pstmt.setInt(4, obj.getAvailableSlots());
			pstmt.setInt(5, obj.getTotalStudentsInCourse());
			ResultSet rs = pstmt.executeQuery();
			// that's it right to update the slots in a table??
			int action = pstmt.executeUpdate();
			if(action > 0) {
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

	// this is used to update a record in the registrationcatalog given the updated
	// object
	// since the course_id is the primary key, it will be the factor to determine
	// how to update the catalog
	public boolean update(Course updatedObject) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "update registrationcatalog set course_id = ? ,course_name = ? , course_department = ? "
					+ "available_slots = ? , total_students_in_course = ? , where course_id = ? ;";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, updatedObject.getCourseId());
			pstmt.setString(2, updatedObject.getCourseName());
			pstmt.setString(3, updatedObject.getCourseDepartment());
			pstmt.setInt(4, updatedObject.getAvailableSlots());
			pstmt.setInt(5, updatedObject.getTotalStudentsInCourse());
			pstmt.setInt(6, updatedObject.getCourseId());

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

	public List<Course> findAll(String type) {

		List<Course> courseList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from registrationcatalog";
			Statement s = (Statement) conn.createStatement();

			ResultSet resultSet = ((java.sql.Statement) s).executeQuery(sql);

			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setCourseDepartment(resultSet.getString("course_department"));
				course.setAvailableSlots(resultSet.getInt("available_slots"));
				course.setTotalStudentsInCourse(resultSet.getInt("total_students_in_course"));

				courseList.add(course);
			}

			return courseList;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	public boolean delete(int id, String type) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "delete from registrationcatalog where course_id = ?;";

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

}
