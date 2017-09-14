package com.chuk3d;

import java.util.Stack;

/**
 * Created by Admin on 13/09/2017.
 */

public class SizedStack<T> extends Stack<T> {

    private int maxSize;

    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }

}