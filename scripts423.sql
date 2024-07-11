
Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах (достаточно получить только имя и возраст студента) школы Хогвартс вместе с названиями факультетов.
select student.name,
student.age,
faculty.name
from student
inner join faculty on student.faculty_id = faculty.id

Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.
select student.name,
student.age,
avatar.file_path
from avatar
inner join student on avatar.student_id = student.id