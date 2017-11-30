package ru.shakirov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
import ru.shakirov.repository.dto.SubjectDto;
import ru.shakirov.repository.exception.SubjectNotFoundException;
import ru.shakirov.service.SubjectService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SubjectControllerTest {

    @Mock
    private SubjectService service;

    @InjectMocks
    private SubjectController controller;

    private MockMvc mockMvc;

    private static final short STUDENT_ID = 1;
    private static final short MATH_ID = 1;
    private static final short LIT_ID = 2;
    private static final String MATH_NAME = "Математика";
    private static final String LIT_NAME = "Литература";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .addPlaceholderValue("apiPath", "api")
                .addPlaceholderValue("version", "v1")
                .build();
    }

    @Test
    public void addSubjects() throws Exception {
        Subject subject1 = createSubject(MATH_ID, MATH_NAME);

        Student student = new Student();
        student.setId((short) 1);
        student.setName("Вася");
        student.getSubjects().add(subject1);
        short[] ids = { MATH_ID };

        when(service.addSubjects(STUDENT_ID, new short[] {MATH_ID})).thenReturn(student);

        MockHttpServletRequestBuilder post = post("/api/v1/students/" + STUDENT_ID + "/subjects")
                .content(new ObjectMapper().writeValueAsBytes(ids))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(post)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Вася")))
                .andExpect(jsonPath("$.subjects[0].id", is((int)MATH_ID)))
                .andExpect(jsonPath("$.subjects[0].name", is(MATH_NAME)))
                .andExpect(jsonPath("$.subjects", hasSize(1)));

        verify(service, times(1)).addSubjects(STUDENT_ID, new short[]{MATH_ID});
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getbyStudentId() throws Exception {
        SubjectDto subject1 = new SubjectDto(MATH_ID, MATH_NAME, true);
        SubjectDto subject2 = new SubjectDto(LIT_ID, LIT_NAME, false);

        when(service.getSubjectsByStudentId(STUDENT_ID)).thenReturn(Arrays.asList(subject1, subject2));

        mockMvc.perform(get("/api/v1/students/" + STUDENT_ID + "/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int)MATH_ID)))
                .andExpect(jsonPath("$[0].name", is(MATH_NAME)))
                .andExpect(jsonPath("$[0].selected", is(true)))
                .andExpect(jsonPath("$[1].id", is((int)LIT_ID)))
                .andExpect(jsonPath("$[1].name", is(LIT_NAME)))
                .andExpect(jsonPath("$[1].selected", is(false)));

        verify(service, times(1)).getSubjectsByStudentId(STUDENT_ID);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void handleAppException() throws Exception {
        when(service.getSubjectsByStudentId(MATH_ID)).thenThrow(SubjectNotFoundException.class);
        mockMvc.perform(get("/api/v1/students/" + STUDENT_ID + "/subjects"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getSubjectsByStudentId(MATH_ID);
        verifyNoMoreInteractions(service);
    }

    private Subject createSubject(int id, String name) {
        Subject subject = new Subject();
        subject.setId((short) id);
        subject.setName(name);
        return subject;
    }

}
