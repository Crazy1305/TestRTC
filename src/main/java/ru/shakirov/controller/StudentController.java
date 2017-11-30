package ru.shakirov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.shakirov.entity.Student;
import ru.shakirov.repository.dto.StudentDto;
import ru.shakirov.repository.exception.StudentNotFoundException;
import ru.shakirov.service.StudentService;
import ru.shakirov.service.SubjectService;

import java.util.Collection;

@RestController
@RequestMapping(value = "${apiPath}/${version}/students", produces = "application/json")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Student> getAllStudents() {
        return studentService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudentById(@PathVariable("id") short id) {
        return studentService.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Student updateStudentById(@PathVariable("id") short id, @RequestBody StudentDto student) {
        return studentService.updateStudent(id, student);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Student addStudent(@RequestBody StudentDto student) {
        return studentService.addStudent(student);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable("id") short id) {
        studentService.deleteById(id);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAppException(StudentNotFoundException ex) {
        return ex.getMessage();
    }

}
