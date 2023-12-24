package dev.luizveronesi.upload.service.strategy;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadType;

public interface UploadStrategy extends StrategyBase<UploadType> {

	String upload(UploadRequest request);
}
