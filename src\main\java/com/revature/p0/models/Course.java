package com.revature.p0.models;

public class Course {

	private int courseId;
	private String courseName;
	private String courseDepartment;
	private int availableSlots;
	private int totalStudentsInCourse;
	
	public Course() {
		
	}
	
	public Course(int courseId) {
		this.courseId = courseId;
	}
	
	public Course(String courseName) {
		this.courseName = courseName;
	}
	
	public Course(int courseId, String courseName, String courseDepartment) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDepartment = courseDepartment;
	}
	

	public Course(int courseId, String courseName, String courseDepartment, int availableSlots,
			int totalStudentsInCourse) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDepartment = courseDepartment;
		this.availableSlots = availableSlots;
		this.totalStudentsInCourse = totalStudentsInCourse;
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



	public int getAvailableSlots() {
		return availableSlots;
	}



	public void setAvailableSlots(int availableSlots) {
		this.availableSlots = availableSlots;
	}



	public int getTotalStudentsInCourse() {
		return totalStudentsInCourse;
	}



	public void setTotalStudentsInCourse(int totalStudentsInCourse) {
		this.totalStudentsInCourse = totalStudentsInCourse;
	}

	
}
