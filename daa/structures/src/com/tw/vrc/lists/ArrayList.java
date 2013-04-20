package com.tw.vrc.lists;

/**
 *
 * log2n doubling for n insertions if we start with a array of size 1
 */
public class ArrayList<T extends Object> {
    private Object[] array;

    public ArrayList(Integer size) {
        this.array = new Object[size];
    }

    public void add(T t) {
        if (addToArray(t)) return;
        Object[] newArray = new Object[2 * array.length];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[i];
        array = newArray;
        addToArray(t);
    }

    private boolean addToArray(T t) {
        boolean isAdded = false;
        for (int i = 0; i < array.length; i++)
            if (array[i] == null) {
                array[i] = t;
                isAdded = true;
                break;
            }
        return isAdded;
    }

    public Integer size() {
        return array.length;
    }

}
