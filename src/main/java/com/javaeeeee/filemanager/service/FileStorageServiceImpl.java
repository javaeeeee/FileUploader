package com.javaeeeee.filemanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Resource> retrieve(Path path) throws MalformedURLException {
        Resource resource = new UrlResource(path.toUri());
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
    public void store(MultipartFile file, Path path) throws IOException {
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }
}
