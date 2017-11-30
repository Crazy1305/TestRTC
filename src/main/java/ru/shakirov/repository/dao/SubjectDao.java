package ru.shakirov.repository.dao;

import ru.shakirov.entity.Subject;

import java.io.Serializable;
import java.util.Collection;

public interface SubjectDao {
    Collection<Subject> getAll();
    Subject getById(short id);
    Subject addSubject(Subject subject);
    Subject updateSubject(Subject subject);
    void deleteSubject(Subject subject);
}
