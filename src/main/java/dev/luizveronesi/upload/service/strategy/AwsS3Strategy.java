package dev.luizveronesi.upload.service.strategy;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadType;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwsS3Strategy implements UploadStrategy {

	private final S3Template s3Template;

	public String upload(UploadRequest request) {
		s3Template.upload(request.getBucket(),
				request.getFilename(),
				new ByteArrayInputStream(request.getBytes()));

		return request.getBucket() + "/" + request.getFilename();
	}

	@Override
	public UploadType getStrategyName() {
		return UploadType.AWS;
	}
}