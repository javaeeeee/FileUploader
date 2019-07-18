package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileNotFoundInStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * @throws FileNotFoundInStorageException An exception thromn if file not found.
     */
    Resource loadFileAsResource(String filename) throws FileNotFoundInStorageException;

    /**
     * Stores the file provided.
     *
     * @param file A file to store.
     */
    FileMetadata save(MultipartFile file);
}
