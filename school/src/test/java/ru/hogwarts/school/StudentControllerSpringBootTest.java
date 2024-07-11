package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerSpringBootTest {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private final String url = "http://localhost:" + port + "/student?idFac=1";

	@Test
	void contextLoad() throws Exception {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	public void testPost() throws Exception {
		String url = "http://localhost:" + port + "/student";
		Student student = new Student(100L, "Vlad", 27, 1);
		Assertions.assertThat(this.testRestTemplate.postForObject(url, student, String.class)).isNotEmpty();
		studentRepository.delete(student);
	}

	@Test
	public void testGetAll() throws Exception {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);
		Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class)).isNotEmpty();
		studentRepository.delete(student);
	}

	@Test
	public void testPut() throws Exception {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);

		String url = "http://localhost:" + port + "/student";
		Student studentUpd = new Student(100L, "Vladimir", 50, 1);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Student> requestEntity = new HttpEntity<>(studentUpd, headers);
		ResponseEntity<Student> response = this.testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Student.class, student.getId());
		Assertions.assertThat(response).isNotNull();
		studentRepository.deleteById(student.getId());
	}

	@Test
	public void testGet() throws Exception {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);

		String url = "http://localhost:" + port + "/student/{id}";
		Assertions.assertThat(testRestTemplate.getForObject(url, Student.class, student.getId())).isNotNull();
		studentRepository.deleteById(student.getId());
	}

    @Test
    public void testGetStudentByAge() throws Exception {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);

        String url = "http://localhost:" + port + "/student/getAge?age=27";
        Assertions.assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();

		studentRepository.deleteById(student.getId());
    }

    @Test
    public void testGetAgeBetween() {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);

        String url = "http://localhost:" + port + "/student/getAgeBetween?minAge=26&maxAge=100";
        Assertions.assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();

		studentRepository.deleteById(student.getId());
	}

	@Test
	public void testGetFaculty() throws Exception {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);

		String url = "http://localhost:" + port + "/student/{id}/faculty";
		Assertions.assertThat(testRestTemplate.getForObject(url, Faculty.class, student.getFacultyId())).isNotNull();

		studentRepository.deleteById(student.getId());
	}

	@Test
	public void testDelete() throws Exception {
		Student student = new Student(100L, "Vlad", 27, 1);
		studentRepository.save(student);

		String url = "http://localhost:" + port + "/student/{id}";
		ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, student.getId());
		boolean result = response.getStatusCode().is2xxSuccessful();
		Assertions.assertThat(result).isTrue();
	}
}
