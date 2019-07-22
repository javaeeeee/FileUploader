package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.exception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Used to store files is some file storage like S3.
 */
public interface FileStorageService {

    /**
     * Used to retrieve a file file fromm storage.
     *
     * @param path The path to a file.
     * @return File as a resource.
     */
    Optional<Resource> retrieve(String path) throws MalformedURLException;

    /**
     * Used to store a file.
     *
     * @param file A file to store.
     * @param path A path to store a file.
     */
    void store(MultipartFile file, String path) throws FileStorageException;

    /**
     * Generates storage path for a file.
     *
     * @param filename The name of the file
     * @return The path to a file.
     */
    String generatePath(String filename);
}
