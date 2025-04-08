package ru.babich.t1schoollearn.exception;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.resource.NoResourceFoundException;

public class ResourceNotFoundException extends NoResourceFoundException {
    public ResourceNotFoundException(String s) {
        super(HttpMethod.GET,s);
    }
}
