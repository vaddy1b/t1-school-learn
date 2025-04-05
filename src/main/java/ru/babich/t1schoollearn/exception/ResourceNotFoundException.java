package ru.babich.t1schoollearn.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class ResourceNotFoundException extends ChangeSetPersister.NotFoundException {
    public ResourceNotFoundException(String s) {
        System.out.println(s);
    }
}
