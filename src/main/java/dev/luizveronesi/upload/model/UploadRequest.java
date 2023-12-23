package dev.luizveronesi.upload.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadRequest {

    private String folder;

    private String path = "";

    private MultipartFile file;

    private UploadType type;

    private String filename;

    private byte[] bytes;

    private Boolean keepOriginalName = Boolean.FALSE;
}
