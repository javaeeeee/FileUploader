package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A facade used to manipulate stored files.
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    private final FileStorageService fileStorageService;
    private final FileMetadataService fileMetadataService;

    public StorageServiceImpl(FileStorageService fileStorageService, FileMetadataService fileMetadataService) {
        this.fileStorageService = fileStorageService;
        this.fileMetadataService = fileMetadataService;
    }

    @Override
    public List<FileMetadata> listAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Resource> loadFileAsResource(String filename) throws FileStorageException {
        Path path = Paths.get(".").resolve(filename).toAbsolutePath().normalize();
        try {
            return fileStorageService.retrieve(path);
        } catch (MalformedURLException e) {
            log.warn("Can't save file: {}", e.getMessage());
            throw new FileStorageException("Can't retrieve tha file: " + filename);
        }
    }

    @Override
    public FileMetadata save(MultipartFile file) throws FileStorageException {
        try {
            Path path = Paths.get(".")
                    .toAbsolutePath().normalize();
            path.resolve(file.getName());
            fileStorageService.store(file, path);
        } catch (IOException e) {
            log.warn("Error saving file.");
            throw new FileStorageException("You file can't be save right now. Please try again later.");
        }
        return FileMetadata.builder().fileName(file.getName()).originalFileName(file.getOriginalFilename()).fileSize(file.getSize()).build();
    }
}
