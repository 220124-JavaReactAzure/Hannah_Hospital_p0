package com.revature.p0.daos;
import com.revature.p0.util.collections.List;

public interface CrudDAO<T> {
	// create the CRUD operations: create, read, update, delete
	
	
	//create operation
	T create(T obj);
	
	
	// read operations
	List<T> findAll();
	T findById(String id);
	
	
	// update operations
	boolean update(T newUpdatedObj);
	
	
	// delete operations
	boolean delete(String id);
	
}
