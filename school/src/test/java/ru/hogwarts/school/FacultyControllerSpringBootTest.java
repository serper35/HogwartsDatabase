package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerSpringBootTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public FacultyControllerSpringBootTest() {
    }

    @Test
    void contextLoad() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testAdd() throws Exception {
        String url = "http://localhost:" + port + "/faculty";
        Faculty faculty = new Faculty(5L, "Ravenclaw", "brown");
        assertThat(testRestTemplate.postForObject(url, faculty, Faculty.class)).isNotNull();
        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testGet() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "brown");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty/{id}";
        assertThat(testRestTemplate.getForObject(url, Faculty.class, faculty.getId())).isNotNull();
        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testGetAll() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "brown");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty";
        assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();
        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "brown");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty";
        Faculty facultyUpd = new Faculty(5L, "Ravenclaw", "black");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> entity = new HttpEntity<>(facultyUpd, headers);

        ResponseEntity<Faculty> response = testRestTemplate.exchange(url, HttpMethod.PUT, entity, Faculty.class, faculty.getId());
        assertThat(response).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(facultyUpd.getName());
        assertThat(response.getBody().getColor()).isEqualTo(facultyUpd.getColor());
        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testGetByColor() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "black");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty/getByColor?color=black";

        ResponseEntity<List> response = testRestTemplate.getForEntity(url, List.class);
        assertThat(response).isNotNull();
        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testGetByNameIgnoreCaseOrGetByColorIgnoreCase() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "black");
        facultyRepository.save(faculty);

        String url = "http://localhost:" + port + "/faculty/getByNameIgnoreCaseOrGetByColorIgnoreCase?name=Ravenclaw&color=black";
        assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();

        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "black");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty/{id}/students";
        Assertions.assertThat(testRestTemplate.getForObject(url, List.class, faculty.getId())).isNotNull();
        facultyRepository.deleteById(faculty.getId());
    }

    @Test
    public void testDelete() throws Exception {
        Faculty faculty = new Faculty(5L, "Ravenclaw", "black");
        facultyRepository.save(faculty);
        String url = "http://localhost:" + port + "/faculty";
        ResponseEntity<Void> response = (testRestTemplate.exchange(url, HttpMethod.DELETE, null, void.class, faculty.getId()));

        assertThat(response).isNotNull();
    }
}
