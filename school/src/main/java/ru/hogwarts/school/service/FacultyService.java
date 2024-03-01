package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void removeFaculty(Long id);

    Collection<Faculty> getFaculties();

    public List<Faculty> getFacultyByColor(String color);

    Collection<Faculty> getByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Collection<Student> getStudentsByFaculty(Long id);

    String getLongestFacultyName();

    String someMethod();
}
