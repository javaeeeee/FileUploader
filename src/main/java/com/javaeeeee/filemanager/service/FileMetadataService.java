package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;

import java.util.List;
import java.util.Optional;

/**
 * Used to manipulate stored files metadata.
 */
public interface FileMetadataService {

    /**
     * Saves the file's metadata to a database.
     *
     * @param metadata
     */
    void saveMetadata(FileMetadata metadata);

    /**
     * Retrieves file metadata by name.
     *
     * @param filename The name of the file to retrieve metadata.
     * @return Returns Optional containing metadata is found and empty Optional otherwise.
     */
    Optional<FileMetadata> get(String filename);

    /**
     * Retrieves metadata for all stored files.
     *
     * @return The list of metadata of all files stored.
     */
    List<FileMetadata> getAllFiles();
}
