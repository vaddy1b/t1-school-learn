package ru.babich.t1schoollearn.exception;

import java.util.NoSuchElementException;

public class TaskNotFoundException extends NoSuchElementException {

    public TaskNotFoundException(String s) {
        super(s);
    }
}
