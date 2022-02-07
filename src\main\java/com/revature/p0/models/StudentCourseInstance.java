package com.revature.p0.models;

public class StudentCourseInstance {
	
	private int studentCourseId;
	private int studentId;
	private int courseId;
	
	
	public StudentCourseInstance(int studentCourseId) {
		super();
		this.studentCourseId = studentCourseId;
	}

	public StudentCourseInstance() {
		super();
	}

	public int getStudentCourseId() {
		return studentCourseId;
	}

	public void setStudentCourseId(int studentCourseId) {
		this.studentCourseId = studentCourseId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	
	
	
	

}
