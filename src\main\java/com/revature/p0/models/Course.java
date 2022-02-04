package com.revature.p0.models;

public class Course {
	
	private int courseId;
	private String courseName;
	private String courseDepartment; 
	
	
	public Course(int courseId, String courseName, String courseDepartment) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDepartment = courseDepartment;
	}


	public int getCourseId() {
		return courseId;
	}


	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public String getCourseDepartment() {
		return courseDepartment;
	}


	public void setCourseDepartment(String courseDepartment) {
		this.courseDepartment = courseDepartment;
	}
	


	
	
	
	
	

}
