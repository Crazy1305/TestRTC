package ru.shakirov.service;

import ru.shakirov.entity.Student;
import ru.shakirov.repository.dto.StudentDto;
import ru.shakirov.repository.exception.StudentNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public interface StudentService {
    Collection<Student> getAll();
    Student getById(short id);
    Student addStudent(StudentDto student);
    Student updateStudent(short id, StudentDto student);
    void deleteById(short id);
}
