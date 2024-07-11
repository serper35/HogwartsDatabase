package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return facultyRepository.getByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudentsByFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(faculty -> faculty.getStudents())
                .orElseThrow();
    }
}
