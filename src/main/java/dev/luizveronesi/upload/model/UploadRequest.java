package dev.luizveronesi.upload.model;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadRequest {

    private String folder;

    private String path = "";

    private String bucket;

    private MultipartFile file;

    private UploadType type;

    private Boolean keepOriginalName = Boolean.FALSE;

    @Schema(hidden = true)
    private String filename;

    @Schema(hidden = true)
    private byte[] bytes;
}
