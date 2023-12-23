package dev.luizveronesi.upload.service.strategy;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobContainerClient;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadResponse;
import dev.luizveronesi.upload.model.UploadType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageStrategy implements UploadStrategy {

    private final BlobContainerClient blobContainerClient;

    public UploadResponse upload(UploadRequest request) {
        blobContainerClient
                .getBlobClient(request.getFilename())
                .upload(new ByteArrayInputStream(request.getBytes()));

        return UploadResponse.builder()
                .url(request.getPath() + "/" + request.getFilename())
                .build();
    }

    @Override
    public UploadType getStrategyName() {
        return UploadType.AZURE;
    }
}
