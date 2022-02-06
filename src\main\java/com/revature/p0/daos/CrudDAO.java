package com.revature.p0.daos;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.List;

public interface CrudDAO<T> {
	// create the CRUD operations: create, read, update, delete
	
	
	//create operation, this is to create a record in the table given a certain object
	boolean create(T obj);
	
	
	// read operations
	T findByIdAndType(int id, String type);
	
	// create some findByID type function
	List<T> findAll(String type);
	
	
	// update operations
	boolean update(T updatedObject);
	
	
	
	// delete operations
	boolean delete(int id, String type);
	

//Do I really want to have this? or should I just have it's own functions depending upon class
//	List<User> findAllStudents();
//	
//	List<User> findAllFaculty();
	
}
