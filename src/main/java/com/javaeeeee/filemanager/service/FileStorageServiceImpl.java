package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
@Profile("Local")
public class FileStorageServiceImpl implements FileStorageService {

    private static final String ERROR_READING_A_FILE = "You file can't be save right now. Please try again later.";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Resource> retrieve(String path) throws MalformedURLException {
        Resource resource = new UrlResource(Paths.get(path).normalize().toUri());
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
            Files.write(Paths.get(path).normalize().toAbsolutePath(), file.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            log.warn(ERROR_READING_A_FILE);
            throw new FileStorageException(ERROR_READING_A_FILE);
        }
    }

    @Override
    public String generatePath(String filename) {
        return String.format("./%s", filename);
    }
}
