package ru.shakirov.repository.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import ru.shakirov.entity.Subject;
import ru.shakirov.repository.exception.SubjectNotFoundException;

import java.io.Serializable;
import java.util.Collection;

@Repository
@DependsOn(value = "sessionFactory")
public class SubjectDaoImpl implements SubjectDao {

    private SessionFactory sessionFactory;

    @Autowired
    public SubjectDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Collection<Subject> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Subject.class).list();
    }

    public Subject getById(short id) {
        Subject subject = sessionFactory.getCurrentSession().get(Subject.class, id);
        if (subject == null) {
            throw new SubjectNotFoundException(id);
        }
        return subject;
    }

    public Subject addSubject(Subject subject) {
        sessionFactory.getCurrentSession().save(subject);
        return subject;
    }

    public Subject updateSubject(Subject subject) {
        sessionFactory.getCurrentSession().update(subject);
        return subject;
    }

    public void deleteSubject(Subject subject) {
        sessionFactory.getCurrentSession().delete(subject);
    }
}
