package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.response.LastFiveStudentsGroupByAge;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student,
                                       @RequestParam Long idFac) {
        Student student1 = studentService.addStudent(student, idFac);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student student1 = studentService.updateStudent(student);
        if (student1 == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("getAgeBetween")
    public Collection<Student> getAgeBetween(@RequestParam(required = false) int minAge,
                                             @RequestParam(required = false) int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("getAge")
    public ResponseEntity<Collection<Student>> getStudentByAge(@RequestParam int age) {
        Collection<Student> getByAge = studentService.getStudentByAge(age);
        if (getByAge.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentByAge(age));
    }

    @GetMapping("{id}/faculty")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("sumOfStudents")
    public ResponseEntity<Integer> getSumOfStudents() {
        return ResponseEntity.ok(studentService.getSumOfStudents());
    }

    @GetMapping("avgAgeOfStudents")
    public ResponseEntity<Double> getAvgAgeOfStudents() {
        return ResponseEntity.ok(studentService.getAvgAgeOfStudents());
    }

    @GetMapping("lastFiveStudentsGroupById")
    public ResponseEntity<List<Student>> getLastFiveStudentsGroupById() {
        return ResponseEntity.ok(studentService.getLastFiveStudentsGroupById());
    }

    @GetMapping("getStudentsNamesStartingWithLetterA")
    public ResponseEntity<List<String>> getStudentsNamesStartingWithLetterA() {
        return ResponseEntity.ok(studentService.studentsNameA());
    }

    @GetMapping("studentsNameStartWith")
    public ResponseEntity<List<String>> studentsNameStartWith(String letter) {
        return ResponseEntity.ok(studentService.studentsNameStartWith(letter));
    }

    @GetMapping("avgAgeOfStudentsByStream")
    public ResponseEntity<Double> avgAgeOfStudents() {
        return ResponseEntity.ok(studentService.avgAgeOfStudents());
    }
}
