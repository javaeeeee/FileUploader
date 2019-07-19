package com.javaeeeee.filemanager.repository;

import com.javaeeeee.filemanager.domain.FileMetadata;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * A repository to store/retrieve file metadata in a database.
 */
public interface FileMetadataRepository extends CrudRepository<FileMetadata, Long> {
    /**
     * Extracts metadata by the name of a file.
     *
     * @param fileName The name of a file.
     * @return Metaadata object
     */
    Optional<FileMetadata> findByFileName(String filename);
}
