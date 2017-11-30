package ru.shakirov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shakirov.entity.Student;
import ru.shakirov.repository.dto.SubjectDto;
import ru.shakirov.repository.exception.SubjectNotFoundException;
import ru.shakirov.service.SubjectService;

import java.util.Collection;

@RestController
@RequestMapping(value = "${apiPath}/${version}/students/{studentId}/subjects", produces = "application/json")
public class SubjectController {

    private SubjectService service;

    @Autowired
    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<SubjectDto> getAllSubjects(@PathVariable(name = "studentId") short studentId) {
        return service.getSubjectsByStudentId(studentId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Student addSubject(@PathVariable(name = "studentId") short studentId,
                              @RequestBody short[] subjects) {
        return service.addSubjects(studentId, subjects);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAppException(SubjectNotFoundException ex) {
        return ex.getMessage();
    }
}
