package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int MAX_RANGE = 10;
    private Object[] objects = new Object[MAX_RANGE];
    private int size = 0;

    @Override
    public void add(T value) {
        checkLength(size);
        objects[size] = value;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAndSize(size + 1, index);
        checkLength(size);
        System.arraycopy(objects, index, objects, index + 1, size - index);
        objects[index] = value;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndexAndSize(size, index);
        return (T) objects[index];
    }

    @Override
    public void set(T value, int index) {
        checkIndexAndSize(size, index);
        remove(index);
        add(value, index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndexAndSize(size, index);
        Object removedObject = objects[index];
        System.arraycopy(objects, index + 1, objects, index, size - index - 1);
        size--;
        return (T) removedObject;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(T element) {
        T removedObject;
        for (int i = 0; i < size; i++) {
            if (element == objects[i]
                    || objects[i] != null
                    && objects[i].equals(element)) {
                removedObject = (T) objects[i];
                remove(i);
                return removedObject;
            }
        }
        throw new NoSuchElementException("Cant find " + element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private void grow() {
        Object[] newObject = new Object[(int) (objects.length * 1.5)];
        System.arraycopy(objects, 0, newObject, 0, objects.length);
        objects = newObject;
    }

    public void checkIndexAndSize(int size, int index) {
        if (index < 0) {
            throw new ArrayListIndexOutOfBoundsException("Index less then 0: " + index);
        }
        if (size <= index) {
            throw new ArrayListIndexOutOfBoundsException("Index larger then size: "
                    + "index: " + index
                    + "size: " + size);
        }
    }

    public void checkLength(int size) {
        if (size >= objects.length) {
            grow();
        }
    }
}
