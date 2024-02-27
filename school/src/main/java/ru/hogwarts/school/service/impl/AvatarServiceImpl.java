package ru.hogwarts.school.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.awt.print.Pageable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    @Value("${path.to.avatars.folder}")
    private String avatarDir;

    private AvatarRepository avatarRepository;
    private StudentService studentService;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("method uploadAvatar was invoked");
        Student student = studentService.getStudent(studentId);
        Path path = Path.of(avatarDir, studentId + "." + getExctension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.deleteIfExists(path);
        Files.createDirectories(path.getParent());

        try(
            InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(path, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    public String getExctension(String fileName) {
        logger.info("method getExctension was invoked");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long id) {
        logger.info("method findAvatar was invoked");
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    public List<Avatar> findALL(int page, int limit) {
        logger.info("method findALL was invoked");
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        return avatarRepository.findAll(pageRequest).getContent();
    }


}
