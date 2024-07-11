package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student faculty);

    Student getStudent(Long id);

    Student updateStudent(Student faculty);

    void removeStudent(Long id);

    Collection<Student> getStudentByAge(int age);
}
