package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("method addFaculty was invoked");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        logger.info("method getFaculty was invoked");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("method updateFaculty was invoked");
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        logger.info("method removeFaculty was invoked");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFaculties() {
        logger.info("method getFaculties was invoked");
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultyByColor(String color) {
        logger.info("method getFacultyByColor was invoked");
        return facultyRepository.findAll()
                .stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("method getByNameIgnoreCaseOrColorIgnoreCase was invoked");
        return facultyRepository.getByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudentsByFaculty(Long id) {
        logger.info("method getStudentsByFaculty was invoked");
        return facultyRepository.findById(id)
                .map(faculty -> faculty.getStudents())
                .orElseThrow();
    }
}
