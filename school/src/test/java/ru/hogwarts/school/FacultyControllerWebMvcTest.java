package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FacultyService facultyService;

    @Test
    public void testGet() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty(1L, "A", "B");

        Mockito.when(facultyService.getFaculty(id)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/{id}", id))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.name").value(faculty.getName()));

    }

    @Test
    public void testAdd() throws Exception {
        Faculty faculty = new Faculty(1L, "A", "B");

        Mockito.when(facultyService.addFaculty(faculty)).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andDo(print());
    }

    @Test
    public void testUpdate() throws Exception {
        Faculty faculty = new Faculty(1L, "A", "B");

        Mockito.when(facultyService.updateFaculty(faculty)).thenReturn(faculty);

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andDo(print());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/faculty/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        Faculty faculty = new Faculty(1L, "A", "B");
        List<Faculty> faculties = new ArrayList<>(List.of(faculty));

        Mockito.when(facultyService.getFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andDo(print());
    }

    @Test
    public void testGetByColor() throws Exception {
        Faculty faculty = new Faculty(1L, "A", "B");
        List<Faculty> faculties = new ArrayList<>(List.of(faculty));

        Mockito.when(facultyService.getFacultyByColor("Green")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/getByColor?color=Green"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andDo(print());
    }

    @Test
    public void getByNameIgnoreCaseOrGetByColorIgnoreCase() throws Exception {
        Faculty faculty = new Faculty(1L, "A", "B");
        List<Faculty> faculties = new ArrayList<>(List.of(faculty));

        Mockito.when(facultyService.getByNameIgnoreCaseOrColorIgnoreCase("A", "B")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/getByNameIgnoreCaseOrGetByColorIgnoreCase?name=A&color=B"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andDo(print());
    }

    @Test
    public void getStudentsByFaculty() throws Exception {
        Long id = 1L;
        Student student = new Student(1L, "A", 20, 1);
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(facultyService.getStudentsByFaculty(id)).thenReturn(students);

        mockMvc.perform(get("/faculty/{id}/students", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()))
                .andDo(print());
    }
}
