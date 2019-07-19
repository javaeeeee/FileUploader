package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface StorageService {
    /**
     * Loads metadata for all saved files.
     *
     * @return A List of file metadata objects.
     */
    List<FileMetadata> listAll();

    /**
     * Loads file by its name.
     *
     * @param filename The name of the file.
     * @return A Resource object that allows file manipulation.
     */
    Optional<Resource> loadFileAsResource(String filename) throws FileStorageException;

    /**
     * Stores the file provided.
     *
     * @param file A file to store.
     */
    FileMetadata save(MultipartFile file) throws FileStorageException;
}
