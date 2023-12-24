package dev.luizveronesi.upload.model;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadRequest {

    private String folder;

    private String bucket;

    @NotNull
    private MultipartFile file;

    @NotNull
    private UploadType type;

    @NotNull
    private Boolean keepOriginalName = Boolean.FALSE;

    @Schema(hidden = true)
    private String filename;

    @Schema(hidden = true)
    private byte[] bytes;
}
