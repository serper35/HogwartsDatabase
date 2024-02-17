package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.DTO.AvatarDTO;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("avatar")
public class AvatarController {
    @Autowired
    private AvatarService avatarService;

    @Autowired
    private AvatarMapper avatarMapper;

    public AvatarController(AvatarService avatarService, AvatarMapper avatarMapper) {
        this.avatarService = avatarService;
        this.avatarMapper = avatarMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> avatarUpload(@RequestParam Long studentId,
                                               @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("findAvatar")
    public void getAvatar(@RequestParam Long id,
                          HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength(avatar.getData().length);
            is.transferTo(os);
        }
    }

    @GetMapping("findAvatarFromDB")
    public ResponseEntity<byte[]> findAvatarFromDB(@RequestParam Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("getPageableAvatar")
    public List<AvatarDTO> getAll(@RequestParam("page") int page,
                                  @RequestParam("limit") int limit) {
        return avatarService.findALL(page, limit).stream()
                .map(avatar -> avatarMapper.avatarToAvatarDTO(avatar))
                .collect(Collectors.toList());
    }
}
