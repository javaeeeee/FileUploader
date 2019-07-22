package com.javaeeeee.filemanager.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.javaeeeee.filemanager.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

/**
 * Used to store files in AWS S3 bucket.
 */
@Slf4j
@Service
@Profile("AWS")
public class AwsFileStorageServiceImpl implements FileStorageService {
    protected static final String ERROR_SAVING_FILE = "You file can't be save right now. Please try again later.";
    private AmazonS3 s3Client;
    private ResourceLoader resourceLoader;
    private final String bucketName;

    public AwsFileStorageServiceImpl(AmazonS3 s3Client, ResourceLoader resourceLoader, @Value("${aws.s3.bucket.name}") String bucketName) {
        this.s3Client = s3Client;
        this.resourceLoader = resourceLoader;
        this.bucketName = bucketName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Resource> retrieve(String path) throws MalformedURLException {
        Resource resource = resourceLoader.getResource(String.format("s3://%s/%s", bucketName, path));
        if (resource.exists()) {
            return Optional.of(resource);
        } else {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(MultipartFile file, String path) throws FileStorageException {
        try {
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            Upload upload = tm.upload(bucketName, path, file.getInputStream(), objectMetadata);
            log.info("File upload started.");

            upload.waitForCompletion();
            log.info("File upload complete");
        } catch (InterruptedException | SdkClientException | IOException e) {
            log.warn(ERROR_SAVING_FILE);
            log.warn(e.toString());
            throw new FileStorageException(ERROR_SAVING_FILE);
        }
    }

    @Override
    public String generatePath(String filename) {
        return filename;
    }
}
