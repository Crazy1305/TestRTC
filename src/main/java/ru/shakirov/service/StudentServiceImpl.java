package ru.shakirov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakirov.entity.Student;
import ru.shakirov.repository.dao.StudentDao;
import ru.shakirov.repository.dto.StudentDto;

import java.io.Serializable;
import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentDao dao;

    @Autowired
    public StudentServiceImpl(StudentDao dao) {
        this.dao = dao;
    }

    public Collection<Student> getAll() {
        return dao.getAll();
    }

    public Student getById(short id) {
        return dao.getById(id);
    }

    @Transactional
    public Student addStudent(StudentDto student) {
        Student inserted = new Student();
        inserted.setName(student.getName());
        return dao.addStudent(inserted);
    }

    @Transactional
    public Student updateStudent(short id, StudentDto student) {
        Student updated = dao.getById(id);
        updated.setName(student.getName());
        return dao.updateStudent(updated);
    }

    @Transactional
    public void deleteById(short id) {
        Student student = dao.getById(id);
        dao.deleteStudent(student);
    }
}
