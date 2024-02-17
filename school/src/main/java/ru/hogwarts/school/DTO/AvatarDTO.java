package ru.hogwarts.school.DTO;

import java.util.Objects;

public class AvatarDTO {
    private Long id;
    private long fileSize;
    private String mediaType;
    private long student_id;

    public AvatarDTO(Long id, long fileSize, String mediaType, long student_id) {
        this.id = id;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.student_id = student_id;
    }

    public AvatarDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDTO avatarDTO = (AvatarDTO) o;
        return fileSize == avatarDTO.fileSize && student_id == avatarDTO.student_id && Objects.equals(id, avatarDTO.id) && Objects.equals(mediaType, avatarDTO.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileSize, mediaType, student_id);
    }

    @Override
    public String toString() {
        return "AvatarDTO{" +
                "id=" + id +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", student_id=" + student_id +
                '}';
    }
}
