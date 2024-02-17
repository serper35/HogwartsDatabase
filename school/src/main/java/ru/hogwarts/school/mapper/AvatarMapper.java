package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.DTO.AvatarDTO;

@Component
public class AvatarMapper {
    public AvatarDTO avatarToAvatarDTO(Avatar avatar) {
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setId(avatar.getId());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setFileSize(avatar.getFileSize());
        avatarDTO.setStudent_id(avatar.getStudent().getId());
        return avatarDTO;
    }

}
