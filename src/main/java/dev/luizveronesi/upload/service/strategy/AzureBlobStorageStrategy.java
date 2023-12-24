package dev.luizveronesi.upload.service.strategy;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;

import com.azure.storage.blob.BlobContainerClient;

import dev.luizveronesi.upload.model.UploadRequest;
import dev.luizveronesi.upload.model.UploadType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageStrategy implements UploadStrategy {

    private final BlobContainerClient blobContainerClient;

    public String upload(UploadRequest request) {
        blobContainerClient
                .getBlobClient(request.getFilename())
                .upload(new ByteArrayInputStream(request.getBytes()));

        return blobContainerClient.getBlobContainerName() + "/" + request.getFilename();
    }

    @Override
    public UploadType getStrategyName() {
        return UploadType.AZURE;
    }
}
