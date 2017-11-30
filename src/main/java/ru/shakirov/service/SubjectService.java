package ru.shakirov.service;

import ru.shakirov.entity.Student;
import ru.shakirov.repository.dto.SubjectDto;

import java.util.Collection;

public interface SubjectService {
    Collection<SubjectDto> getSubjectsByStudentId(short studentId);
    Student addSubjects(short id, short[] subjects);
}
