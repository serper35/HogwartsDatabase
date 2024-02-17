package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.response.AvgAgeOfStudents;
import ru.hogwarts.school.response.SumOfStudents;
//import ru.hogwarts.school.response.LastFiveStudentsGroupByAge;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student faculty, Long idFac);

    Student getStudent(Long id);

    Student updateStudent(Student faculty);

    void removeStudent(Long id);

    Collection<Student> getStudentByAge(int age);

    Faculty getFaculty(Long id);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> getAll();

    List<SumOfStudents> getSumOfStudents();

    List<AvgAgeOfStudents>  getAvgAgeOfStudents();

    List<Student> getLastFiveStudentsGroupById();
}

