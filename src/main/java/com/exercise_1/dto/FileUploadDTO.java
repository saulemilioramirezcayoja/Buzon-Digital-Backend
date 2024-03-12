package com.exercise_1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadDTO {
    private Long requestId;
    private String fileName;
    private Long fileSize;
    private String attachmentType;
    private String base64EncodedFile;
}