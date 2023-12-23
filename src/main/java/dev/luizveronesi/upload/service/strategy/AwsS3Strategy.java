package dev.luizveronesi.upload.service.strategy;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadResponse;
import dev.luizveronesi.upload.model.UploadType;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwsS3Strategy implements UploadStrategy {

	@Value("${app.aws.bucket:}")
	private String awsBucket;

	private final S3Template s3Template;

	public UploadResponse upload(UploadRequest request) {
		s3Template.upload(awsBucket,
				request.getFilename(),
				new ByteArrayInputStream(request.getBytes()));

		return UploadResponse.builder()
				.url(request.getPath() + "/" + request.getFilename())
				.build();
	}

	@Override
	public UploadType getStrategyName() {
		return UploadType.AWS;
	}
}