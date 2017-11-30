package ru.shakirov.repository.exception;

import java.io.Serializable;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Serializable id) {
        super("Student with identifier " + id + " not found");
    }
}
