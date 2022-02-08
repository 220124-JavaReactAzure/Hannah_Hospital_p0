package com.revature.p0.daos;
import com.revature.p0.models.User;
import com.revature.p0.util.collections.List;

public interface CrudDAO<T> {
	// create the CRUD operations: create, read, update, delete
	
	
	//create
	boolean create(T obj);
	
	
	//read
	T findByIdAndType(int id, String type);
	List<T> findAll(String type);
	
	
	//update
	boolean update(T updatedObject);
	
	
	
	//delete
	boolean delete(int ID);
	

	
//Do I really want to have this? or should I just have it's own functions depending upon class
//	List<User> findAllStudents();
//	
//	List<User> findAllFaculty();
	
	
	
}
