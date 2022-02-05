package com.revature.p0.daos;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.List;

public interface CrudDAO<T> {
	// create the CRUD operations: create, read, update, delete
	
	
	//create operation
	T create(T obj);
	
	
	// read operations
	T findByIdAndType(int id, String type);
	
	
	// update operations
	boolean update(T updatedUser, String attribute);
	
	// i will need an update method for courses
	
	
	
	// delete operations
	boolean delete(int id);


	List<User> findAllStudents();
	
	List<User> findAllFaculty();
	
}
