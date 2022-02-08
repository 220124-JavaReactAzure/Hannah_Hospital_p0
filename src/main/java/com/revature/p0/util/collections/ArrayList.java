package com.revature.p0.util.collections;


import java.util.Arrays;


public class ArrayList<T> implements List<T> {

    protected int size;
    protected Object[] elements;

    public ArrayList() {
        elements = new Object[16];
    }

    public ArrayList(int initialCapacity) {
        elements = new Object[initialCapacity];
    }


    @Override
    public void add(T element) {
        elements[size] = element;
        size++;
        resizeBackingArrayIfNeeded();
    }


    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == null) {
                if (element == null) {
                    return true;
                }
            } else if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if ((elements[i] == null && element == null) || (elements[i] != null && elements[i].equals(element))) {
                removeAtIndex(i);
                size--;
                return true;
            }
        }
        return false;
    }

 
    @Override
    public int size() {
        return size;
    }


    @Override
    @SuppressWarnings({"unchecked"})
    public T get(int index) {
        if (notInRange(index)) throw new IndexOutOfBoundsException();
        return (T) elements[index];
    }

 
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Object[] newElements = resizeWillBeNeeded(size + 1) ? new Object[nextSize()] : new Object[elements.length];
        System.arraycopy(elements, 0, newElements, 0, index);
        System.arraycopy(elements, index, newElements, index + 1, elements.length - index - 1);
        newElements[index] = element;
        size++;
        elements = newElements;
    }


    @Override
    @SuppressWarnings({"unchecked"})
    public T set(int index, T element) {
        if (notInRange(index)) throw new IndexOutOfBoundsException();
        T oldElement = (T) elements[index];
        elements[index] = element;
        return oldElement;
    }


    @Override
    @SuppressWarnings({"unchecked"})
    public T remove(int index) {
        if (notInRange(index)) throw new IndexOutOfBoundsException();
        T oldElement = (T) elements[index];
        removeAtIndex(index);
        size--;
        return oldElement;

    }


    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if ((elements[i] == null && element == null) || (elements[i] != null && elements[i].equals(element))) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public int lastIndexOf(T element) {
        for (int i = size - 1; i >= 0; i--) {
            if ((elements[i] == null && element == null) || (elements[i] != null && elements[i].equals(element))) {
                return i;
            }
        }
        return -1;
    }

    protected boolean notInRange(int index) {
        return index < 0 || index >= size;
    }

    protected void resizeBackingArrayIfNeeded() {
        if (size >= elements.length * 0.75) {
            Object[] newBackingArray = new Object[nextSize()];
            System.arraycopy(elements, 0, newBackingArray, 0, elements.length);
            elements = newBackingArray;
        }
    }

    protected boolean resizeWillBeNeeded(int nextSize) {
        return (nextSize >= elements.length * 0.75);
    }

    protected int nextSize() {
        return (int) (elements.length * 0.5) + elements.length;
    }

    private void removeAtIndex(int index) {
        if (index == 0) {
            Object[] nextElements = new Object[elements.length];
            System.arraycopy(elements, 1, nextElements, 0, elements.length - 2);
            elements = nextElements;
        } else if (index == size() - 1) {
            elements[index] = null;
        } else {
            Object[] newElements = new Object[elements.length];
            System.arraycopy(elements, 0, newElements, 0, index);
            System.arraycopy(elements, index + 1, newElements, index, elements.length - index - 1);
            elements = newElements;
        }

    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}