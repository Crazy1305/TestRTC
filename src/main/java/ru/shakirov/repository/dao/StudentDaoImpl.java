package ru.shakirov.repository.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shakirov.entity.Student;
import ru.shakirov.repository.exception.StudentNotFoundException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
@DependsOn(value = "sessionFactory")
public class StudentDaoImpl implements StudentDao {

    private SessionFactory sessionFactory;

    @Autowired
    public StudentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Collection<Student> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Student.class);
        List<Student> list = criteria.list();
        return list;
    }

    public Student getById(short id) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.get(Student.class, id);
        if (student == null) {
            throw new StudentNotFoundException(id);
        }
        return student;
    }

    public Student addStudent(Student student) {
        sessionFactory.getCurrentSession().save(student);
        return student;
    }

    public Student updateStudent(Student student) {
        sessionFactory.getCurrentSession().update(student);
        return student;
    }

    public void deleteStudent(Student student) {
        sessionFactory.getCurrentSession().delete(student);
    }

}
