package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
//import ru.hogwarts.school.response.LastFiveStudentsGroupByAge;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private FacultyService facultyService;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository, FacultyService facultyService) {
        this.studentRepository = studentRepository;
        this.facultyService = facultyService;
    }

    public Student addStudent(Student student, Long idFac) {
        logger.info("method addStudent was invoked");
        student.setFacultyId(facultyService.getFaculty(idFac));
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        logger.info("method getStudent was invoked");
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        logger.info("method updateStudent was invoked");
        return studentRepository.save(student);
    }

    public void removeStudent(Long id) {
        logger.info("method removeStudent was invoked");

        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        logger.info("method getAllStudent was invoked");

        return studentRepository.findAll();
    }

    public List<Student> getStudentByAge(int age) {
        logger.info("method getStudentByAge was invoked");

        return studentRepository.findAll().
                stream().
                filter(s -> s.getAge() == age).
                collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.info("method findByAgeBetween was invoked");

        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Faculty getFaculty(Long id) {
        logger.info("method getFaculty was invoked");

        return studentRepository.findById(id).get().getFacultyId();
    }

    public int getSumOfStudents() {
        logger.info("method getSumOfStudents was invoked");

        return studentRepository.getSumOfStudents();
    }

    public double  getAvgAgeOfStudents() {
        logger.info("method getAvgAgeOfStudents was invoked");

        return studentRepository.getAvgAgeOfStudents();
    }

    public List<Student> getLastFiveStudentsGroupById() {
        logger.info("method getLastFiveStudentsGroupById was invoked");

        return studentRepository.getLastFiveStudentsGroupById();
    }

    public List<String> studentsNameA() {
        logger.info("method studentsNameA was invoked");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(n -> n.toUpperCase().startsWith("A"))
                .sorted(Comparator.naturalOrder())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public List<String> studentsNameStartWith(String letter) {
        logger.info("method studentsNameStartWith was invoked");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(n -> n.toUpperCase().startsWith(letter.toUpperCase()))
                .sorted(Comparator.naturalOrder())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public Double avgAgeOfStudents() {
        logger.info("method avgAgeOfStudents was invoked");
        return studentRepository.findAll().stream()
                .mapToDouble(student -> student.getAge())
                .average()
                .orElse(0.0);
    }


}
