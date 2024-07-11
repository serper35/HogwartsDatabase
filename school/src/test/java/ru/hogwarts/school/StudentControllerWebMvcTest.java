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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;

    @Test
    public void testGet() throws Exception {
        Long id = 1L;
        Student student = new Student(1L, "A", 20, 1);

        Mockito.when(studentService.getStudent(id)).thenReturn(student);

        mockMvc.perform(get("/student/{id}", id))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andDo(print());
    }

    @Test
    public void testAdd() throws Exception {
        Long id = 1L;
        Student student = new Student(1L, "A", 20, 1);

        Mockito.when(studentService.addStudent(student, id)).thenReturn(student);

        mockMvc.perform(post("/student?idFac=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andDo(print());
    }

    @Test
    public void testUpdate() throws Exception {
        Student student = new Student(1L, "A", 20, 1);

        Mockito.when(studentService.updateStudent(student)).thenReturn(student);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andDo(print());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/student/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        Student student = new Student(1L, "A", 20, 1);
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentService.getAll()).thenReturn(students);

        mockMvc.perform(get("student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    public void testGetAge() throws Exception {
        Long id = 1L;
        Student student = new Student(1L, "A", 20, 1);
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentService.getStudentByAge(20)).thenReturn(students);

        mockMvc.perform(get("/student/getAge?age=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    public void testGetAgeBetween() throws Exception {
        Long id = 1L;
        Student student = new Student(1L, "A", 20, 1);
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentService.findByAgeBetween(10, 100)).thenReturn(students);

        mockMvc.perform(get("/student/getAgeBetween?minAge=10&maxAge=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    public void testGetFaculty() throws Exception {
        Long id = 1L;
        Student student = new Student(1L, "A", 20, 1);
        Faculty faculty = new Faculty(1L, "A", "B");

        Mockito.when(studentService.getFaculty(id)).thenReturn(faculty);

        mockMvc.perform(get("/student/{id}/faculty", id))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andDo(print());
    }
}
