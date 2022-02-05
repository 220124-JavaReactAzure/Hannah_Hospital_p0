package com.revature.p0.models;

public class Course {

	private int courseId;
	private String courseName;
	private String courseDepartment;
	private int availabeSlots;
	private int totalStudentsInCourse;
	
	public Course(int courseId) {
		this.courseId = courseId;
	}
	public Course(int courseId, String courseName, String courseDepartment) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDepartment = courseDepartment;
	}
	
	

	public Course(int courseId, String courseName, String courseDepartment, int availabeSlots,
			int totalStudentsInCourse) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDepartment = courseDepartment;
		this.availabeSlots = availabeSlots;
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



	public int getAvailabeSlots() {
		return availabeSlots;
	}



	public void setAvailabeSlots(int availabeSlots) {
		this.availabeSlots = availabeSlots;
	}



	public int getTotalStudentsInCourse() {
		return totalStudentsInCourse;
	}



	public void setTotalStudentsInCourse(int totalStudentsInCourse) {
		this.totalStudentsInCourse = totalStudentsInCourse;
	}

	
}
