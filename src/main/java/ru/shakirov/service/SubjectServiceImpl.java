package ru.shakirov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shakirov.entity.Student;
import ru.shakirov.entity.Subject;
import ru.shakirov.repository.dao.StudentDao;
import ru.shakirov.repository.dao.SubjectDao;
import ru.shakirov.repository.dto.SubjectDto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private StudentDao studentDao;
    private SubjectDao subjectDao;

    @Autowired
    public SubjectServiceImpl(StudentDao studentDao, SubjectDao subjectDao) {
        this.studentDao = studentDao;
        this.subjectDao = subjectDao;
    }

    public Collection<SubjectDto> getSubjectsByStudentId(short studentId) {
        Set<SubjectDto> result = new HashSet<>();
        Collection<Subject> subjects = subjectDao.getAll();
        Student student = studentDao.getById(studentId);
        for (Subject subject : subjects) {
            SubjectDto newSubject = new SubjectDto(subject.getId(), subject.getName());
            if (student.getSubjects().contains(subject)) {
                newSubject.setSelected(true);
            }
            result.add(newSubject);
        }
        return result;
    }

    @Transactional
    public Student addSubjects(short id, short[] subjects) {
        Student student = studentDao.getById(id);
        student.getSubjects().clear();
        for (short subject : subjects) {
            student.getSubjects().add(subjectDao.getById(subject));
        }
        return studentDao.updateStudent(student);
    }
}
