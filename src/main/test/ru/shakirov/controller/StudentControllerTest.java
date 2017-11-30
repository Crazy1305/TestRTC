package ru.shakirov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.shakirov.entity.Student;
import ru.shakirov.entity.Subject;
import ru.shakirov.repository.dto.StudentDto;
import ru.shakirov.repository.exception.StudentNotFoundException;
import ru.shakirov.service.StudentService;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class StudentControllerTest {

    @Mock
    private StudentService service;

    @InjectMocks
    private StudentController controller;

    private MockMvc mockMvc;

    private static final short ID_VASYA = 1;
    private static final short ID_FEDYA = 2;
    private static final String NAME_VASYA = "Вася";
    private static final String NAME_FEDYA = "Федя";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addPlaceholderValue("apiPath", "api")
                .addPlaceholderValue("version", "v1")
                .build();
    }

    @Test
    public void getAllStudents() throws Exception {
        Student student1 = createStudent(ID_VASYA, NAME_VASYA);
        Student student2 = createStudent(ID_FEDYA, NAME_FEDYA);

        when(service.getAll()).thenReturn(Arrays.asList(student1, student2));
        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int)ID_VASYA)))
                .andExpect(jsonPath("$[0].name", is(NAME_VASYA)))
                .andExpect(jsonPath("$[0].subjects", is(emptyCollectionOf(Subject.class))))
                .andExpect(jsonPath("$[1].id", is((int)ID_FEDYA)))
                .andExpect(jsonPath("$[1].name", is(NAME_FEDYA)))
                .andExpect(jsonPath("$[1].subjects", is(emptyCollectionOf(Subject.class))));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getStudentById() throws Exception {
        Student student = createStudent(1, NAME_VASYA);

        when(service.getById(ID_VASYA)).thenReturn(student);
        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id", is((int)ID_VASYA)))
                .andExpect(jsonPath("$.name", is(NAME_VASYA)))
                .andExpect(jsonPath("$.subjects", is(emptyCollectionOf(Subject.class))));

        verify(service, times(1)).getById(ID_VASYA);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateStudentById() throws Exception {
        Student student = createStudent(ID_VASYA, NAME_VASYA);
        StudentDto updated = new StudentDto(NAME_VASYA);

        when(service.updateStudent(ID_VASYA, updated)).thenReturn(student);

        MockHttpServletRequestBuilder put = put("/api/v1/students/1")
                .content(new ObjectMapper().writeValueAsBytes(updated))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(put)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id", is((int)ID_VASYA)))
                .andExpect(jsonPath("$.name", is(NAME_VASYA)))
                .andExpect(jsonPath("$.subjects", is(emptyCollectionOf(Subject.class))));

        verify(service, times(1)).updateStudent(ID_VASYA, updated);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void addStudent() throws Exception {
        Student student = createStudent(ID_VASYA, NAME_VASYA);
        StudentDto inserted = new StudentDto(NAME_VASYA);

        when(service.addStudent(inserted)).thenReturn(student);
        MockHttpServletRequestBuilder post = post("/api/v1/students")
                .content(new ObjectMapper().writeValueAsBytes(inserted))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(post)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id", is((int)ID_VASYA)))
                .andExpect(jsonPath("$.name", is(NAME_VASYA)))
                .andExpect(jsonPath("$.subjects", is(emptyCollectionOf(Subject.class))));

        verify(service, times(1)).addStudent(inserted);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteStudent() throws Exception {
        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(ID_VASYA);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void handleAppException() throws Exception {
        when(service.getById(ID_VASYA)).thenThrow(StudentNotFoundException.class);
        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getById(ID_VASYA);
        verifyNoMoreInteractions(service);
    }

    private Student createStudent(int id, String name) {
        Student student = new Student();
        student.setId((short) id);
        student.setName(name);
        student.setSubjects(new HashSet<Subject>());
        return student;
    }

}