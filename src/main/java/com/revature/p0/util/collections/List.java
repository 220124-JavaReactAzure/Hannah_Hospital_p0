package com.revature.p0.util.collections;


public interface List<T> {

	    T get(int index);
	    void add(T element);
	    T set(int index, T element);
	    T remove(int index);
	    int indexOf(T element);
	    int lastIndexOf(T element);

		boolean isEmpty();

		boolean contains(T element);

		boolean remove(T element);

		int size();

		void add(int index, T element);

	}
