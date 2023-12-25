package dev.luizveronesi.upload.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadResponse;
import dev.luizveronesi.upload.model.UploadType;
import dev.luizveronesi.upload.service.factory.UploadServiceFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadService {

	@Value("${app.upload.blocklist}")
	private String[] blockList;

	@Value("${app.upload.allowlist}")
	private String[] allowList;

	private final UploadServiceFactory uploadServiceFactory;

	public UploadResponse execute(UploadRequest request) {
		var uid = UUID.randomUUID().toString();
		var filename = this.generateFilename(request, uid);
		request.setFilename(filename);

		/**
		 * Why byte[]? When using InputStream it is not possible to detect
		 * file type using Tika and work with it later. Once the stream is
		 * opened, it is never the same anymore.
		 */
		var bytes = this.convertToByte(request);
		request.setBytes(bytes);

		var type = this.detectType(bytes);
		this.verifyThreat(type);

		var path = uploadServiceFactory.getStrategy(request.getType()).upload(request);

		return UploadResponse.builder()
				.uid(uid)
				.filetype(type)
				.originalName(request.getFile().getOriginalFilename())
				.name(filename)
				.path(path)
				.build();
	}

	private byte[] convertToByte(UploadRequest request) {
		try {
			return IOUtils.toByteArray(request.getFile().getInputStream());
		} catch (IOException e1) {
			throw new RuntimeException("error reading file");
		}
	}

	/**
	 * Detecting the real type is always more consuming than simple mime detection.
	 * 
	 * This is to be sure!
	 */
	private String detectType(byte[] bytes) {
		try {
			return new Tika().detect(bytes);
		} catch (Exception e) {
			throw new RuntimeException(
					"error detecting file type" + ExceptionUtils.getStackTrace(e));
		}
	}

	private void verifyThreat(String type) {
		for (var threat : allowList) {
			if (type.contains(threat))
				return;
		}

		for (String threat : blockList) {
			if (type.contains(threat)) {
				throw new RuntimeException("possible threat, can't upload.");
			}
		}
	}

	/**
	 * This method generates the filename for an uploaded file. This is name follows
	 * a rule:
	 * Concatenation: loginId|original file name|time in milliseconds
	 * Encrypting: use system default encrypting method
	 * Extension: same as the uploaded file
	 */
	private String generateFilename(UploadRequest request, String uid) {
		String filename = request.getFile().getOriginalFilename();

		if (request.getKeepOriginalName())
			return filename;

		String extension = null;
		if (filename.indexOf(".") > 0)
			extension = StringUtils.substringAfterLast(filename, ".");
		if (extension == null)
			return "not-uploaded";

		StringBuffer sb = new StringBuffer();
		sb.append(uid);
		sb.append(".");
		sb.append(extension.toLowerCase());

		return sb.toString();
	}
}