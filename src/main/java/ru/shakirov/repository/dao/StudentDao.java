package ru.shakirov.repository.dao;

import ru.shakirov.entity.Student;
import ru.shakirov.repository.exception.StudentNotFoundException;

import java.io.Serializable;
import java.util.Collection;

public interface StudentDao {
    Collection<Student> getAll();
    Student getById(short id);
    Student addStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudent(Student student);
}
