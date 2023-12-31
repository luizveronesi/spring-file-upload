package dev.luizveronesi.upload.service.strategy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadType;

@Service
public class LocalStrategy implements UploadStrategy {

	public String upload(UploadRequest request) {
		File dir = new File(request.getFolder());
		if (!dir.exists())
			dir.mkdir();

		File destFile = new File(dir + File.separator + request.getFilename());

		try {
			FileUtils.copyInputStreamToFile(
					new ByteArrayInputStream(request.getBytes()), destFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return destFile.getAbsolutePath();
	}

	@Override
	public UploadType getStrategyName() {
		return UploadType.LOCAL;
	}
}
