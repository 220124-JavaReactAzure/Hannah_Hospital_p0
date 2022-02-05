package com.revature.p0.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.p0.models.Course;
import com.revature.p0.util.datasource.ConnectionFactory;

public class CourseDAO implements CrudDAO<Course> {
	
	
	public Course findById(int courseId) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "select * from registrationCatalog where course_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Course course = new Course(courseId);
				course.setCourseName(rs.getString("course_name"));
				course.setCourseDepartment(rs.getString("course_department"));
				course.setAvailabeSlots(rs.getInt("available_slot"));
				course.setTotalStudentsInCourse(rs.getInt("total_students_in_course"));
				
				return course;
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
}
