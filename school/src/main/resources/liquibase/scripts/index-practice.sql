-- liquibase formatted sql

-- changeset serper:1
CREATE INDEX students_name_index on student (name);

-- changeset serper:2
CREATE INDEX faculty_name_and_color_index on faculty (name, color);