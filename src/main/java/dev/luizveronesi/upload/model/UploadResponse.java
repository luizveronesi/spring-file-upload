package dev.luizveronesi.upload.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadResponse {

	private String uid;
	private String filetype;
	private String path;
	private String name;
	private String originalName;
}
