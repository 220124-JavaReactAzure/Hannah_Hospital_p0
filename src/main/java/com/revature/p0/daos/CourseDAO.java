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

public class CourseDAO implements CrudDAO<Course> {

	// this is to find a certain record in a table, given the courseID and return a
	// course object containing all the info from the table
	public Course findByIdAndType(int courseId, String type) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from registrationcatalog where course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Course course = new Course(courseId);
				course.setCourseName(rs.getString("course_name"));
				course.setCourseDepartment(rs.getString("course_department"));
				course.setAvailableSlots(rs.getInt("available_slots"));
				course.setTotalStudentsInCourse(rs.getInt("total_students_in_course"));

				return course;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
@Override
public void create(Course obj) {
	System.out.println("Hi");
}

// this is for faculty members to create a new course object and register it
// into the database
public void createRecord(Course obj) {

	try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
		String sql = "insert into registrationcatalog(course_id, course_name, course_department, available_slots, total_students_in_course) values (?, ?, ?, ?, ?);";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, obj.getCourseId());
		pstmt.setString(2, obj.getCourseName());
		pstmt.setString(3, obj.getCourseDepartment());
		pstmt.setInt(4, obj.getAvailableSlots());
		pstmt.setInt(5, obj.getTotalStudentsInCourse());
		ResultSet rs = pstmt.executeQuery();
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
}

	
	

	public void updateCourse(Course updatedObject) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "update registrationcatalog set course_id = ? ,course_name = ? , course_department = ? , available_slots = ? , total_students_in_course = ?  where course_id = ? ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, updatedObject.getCourseId());
			pstmt.setString(2, updatedObject.getCourseName());
			pstmt.setString(3, updatedObject.getCourseDepartment());
			pstmt.setInt(4, updatedObject.getAvailableSlots());
			pstmt.setInt(5, updatedObject.getTotalStudentsInCourse());
			pstmt.setInt(6, updatedObject.getCourseId());

			ResultSet rs = pstmt.executeQuery();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public boolean update(Course updatedObject) {
		return true;
	}

	
	
	// I guess this function would be for teachers to look at all courses on the registrationcatalog, even ones that have no available slots
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
	
	
	
	
	// this function will return all courses that are available, where available slots > 0
	public List<Course> findAllAvailableCourses() {

		List<Course> courseList = new ArrayList<>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "select * from registrationcatalog where available_slots > 0 ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet resultSet = pstmt.executeQuery();

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
	
	
	
	

	// this method, given the studentID, returns a list of all of the courses that the student is registered for
	public List<Course> findAllRegisteredCoursesForStudent(int studentID){
		List<Course> courses = new ArrayList<Course>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
//			String sql = "studentcourserecords inner join registrationcatalog on course_name where student_id = ? ";
			String sql = "select * from registrationcatalog inner join studentcourserecords on registrationcatalog.course_id=studentcourserecords.course_id where student_id = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Course course = new Course();
				course.setCourseName(rs.getString("course_name"));
				courses.add(course);
				
			}
			return courses;
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	
	
	// create a function that will return a boolean and tell you if a course, given a course_id, is available or see if it has any open slots
	public boolean isCourseAvailable(int courseID) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			// is this the correct syntax
			String sql = "select * from registrationcatalog where course_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int available_slots = rs.getInt("available_slots");
				if(available_slots > 0) {
					return true;
				}
				else {
					return false;
				}
			}

		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override 
	public boolean delete(int courseID) {
		return true;
	}
	
	
	
	public void deleteRecord(int courseID) {
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "delete from registrationcatalog where course_id = ?";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseID);

			ResultSet rs = pstmt.executeQuery();
			// this is to check if there were any rows affected, if successful return true
			// if not return false
			int count = pstmt.executeUpdate();
	

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		}
	
	
	
	

	}

