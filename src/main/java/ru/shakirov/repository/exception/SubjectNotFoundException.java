package ru.shakirov.repository.exception;

import java.io.Serializable;

public class SubjectNotFoundException extends RuntimeException {

    public SubjectNotFoundException(Serializable id) {
        super("Subject with identifier " + id + " not found");
    }
}
