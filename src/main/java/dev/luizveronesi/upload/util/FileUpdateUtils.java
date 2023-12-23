package dev.luizveronesi.upload.util;

import org.apache.commons.lang3.StringUtils;

import dev.luizveronesi.upload.model.UploadRequest;

public class FileUpdateUtils {

	/**
	 * This method generates the filename for an uploaded file. This is name follows a rule:
	 * Concatenation: loginId|original file name|time in milliseconds
	 * Encrypting: use system default encrypting method
	 * Extension: same as the uploaded file 
	 */
	public static String generateFilename(UploadRequest request, String uid) {
		String filename = request.getFile().getOriginalFilename();
		
		String extension = null;
		if (filename.indexOf(".") > 0) extension = StringUtils.substringAfterLast(filename, ".");
		if (extension == null) return "not-uploaded";
		
		StringBuffer sb = new StringBuffer();
		sb.append(uid);
		sb.append(".");
		sb.append(extension.toLowerCase());
		
		return sb.toString();
	}
}
