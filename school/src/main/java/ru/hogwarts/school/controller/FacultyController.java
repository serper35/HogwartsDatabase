package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {
        Faculty facultyAdded = facultyService.addFaculty(faculty);
        if (facultyAdded == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyAdded);
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty facultyUpdate = facultyService.updateFaculty(faculty);
        if (facultyUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyUpdate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity remove(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getFaculties();
    }

    @GetMapping("getByColor")
    public ResponseEntity<List<Faculty>> getByColor(@RequestParam String color) {
        List<Faculty> getByColor = facultyService.getFacultyByColor(color);
        if (getByColor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getByColor);
    }

    @GetMapping("getByNameIgnoreCaseOrGetByColorIgnoreCase")
    public Collection<Faculty> getByNameIgnoreCaseOrGetByColorIgnoreCase(@RequestParam(required = false) String name,
                                                                         @RequestParam(required = false) String color) {
        return facultyService.getByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @GetMapping("{id}/students")
    public Collection<Student> getStudentsByFaculty(@PathVariable Long id) {
        return facultyService.getStudentsByFaculty(id);
    }
}
